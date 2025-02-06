import React, { useCallback, useEffect, useState } from 'react';

/**
 * 서버에 fetch 요청을 보내는 리액트 훅입니다.
 *
 * @param {String} endpoint - API 호출의 엔드포인트입니다.
 * @param {'POST' | 'PUT' | 'PATCH' | 'DELETE'} method - method입니다. 기본값은 "GET"입니다.
 * @param {Object} request - 서버에 전송할 데이터 객체입니다. "GET" 요청에서는 사용되지 않습니다.
 * @returns {{ data: any, error: string | null, loading: boolean, refetch: Function }}
 * - `data`: 서버에서 반환된 데이터
 * - `error`: 발생한 오류 메시지(없으면 `null`)
 * - `loading`: 데이터 로딩 상태(`true` 또는 `false`)
 * - `refetch`: 데이터 재요청 함수
 */
function useFetch(endpoint, method = 'GET', request = {}) {
  const [data, setData] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const stringifiedRequest = JSON.stringify(request); // 전송 객체를 문자열 형식으로 변환
  const baseUrl = 'http://localhost:8090'; // 서버 url

  // fetch 요청을 보내는 함수
  const fetchData = useCallback(
    async (signal) => {
      setLoading(true);
      setError(null);

      try {
        const response = await fetch(`${baseUrl}${endpoint}`, {
          method,
          headers: { 'Content-Type': 'application/json' },
          body: method !== 'GET' ? stringifiedRequest : undefined,
          signal, // fetch 요청 중단 신호를 받기 위한 signal 연결
        });

        if (!response.ok) {
          throw new Error(`HTTP 에러가 발생했습니다: ${response.status}`);
        }

        const json = await response.json();
        setData(json);
      } catch (error) {
        if (error.name !== 'AbortError') {
          setError(error.message);
        }
      } finally {
        setLoading(false);
      }
    }, // useCallback
    [endpoint, method, stringifiedRequest]
  ); // fetchData

  useEffect(() => {
    const controller = new AbortController(); // fetch 요청의 중단 신호를 제어하는 컨트롤러
    fetchData(controller.signal);

    return () => {
      controller.abort(); // 진행중인 fetch를 중단시키고 AbortError를 발생시킴
    };
  }, [fetchData]);

  // fetch 요청을 다시 보내는 함수
  const refetch = () => {
    const controller = new AbortController();
    fetchData(controller.signal);
  };

  return { data, error, loading, refetch };
}

export default useFetch;
