import { Link } from 'react-router-dom';
import ErrorContainer from '../../components/container/ErrorContainer';
import LoadingContainer from '../../components/container/LoadingContainer';
import useFetch from '../../hooks/useFetch';
import styles from './StoreList.module.css';
import { useCallback, useEffect, useRef, useState } from 'react';

import { IoEyeOutline } from 'react-icons/io5';
import { FaStar, FaHeart } from 'react-icons/fa';

const StoreList = ({ local, foodCategory, theme, sort }) => {
  const [page, setPage] = useState(0); // 현재 페이지 상태
  const [storeList, setStoreList] = useState([]);

  // props 중 값이 전달된 속성만을 배열로 생성
  const queryEntries = Object.entries({
    local,
    foodCategory,
    theme,
    sort,
  }).filter(
    ([key, value]) => value !== undefined && value !== null && value !== ''
  );

  // 필터 변경 시 페이지 초기화
  useEffect(() => {
    setPage(0);
    setStoreList([]);
  }, [local, foodCategory, theme, sort]);

  // 배열로 쿼리 파라미터 생성
  const queryString = new URLSearchParams(queryEntries).toString();

  // 쿼리 파라미터 존재 여부에 따라 url 생성
  const url = queryString ? `&${queryString}` : '';

  // 가게 리스트 fetch
  const { data, error, loading, refetch } = useFetch(
    `/api/stores?page=${page}${url}`
  );
  useEffect(() => {
    if (loading || !data) return;
    setStoreList((prevStores) => {
      // 페이지가 0이 아닌 경우 가게 리스트를 누적
      return [...prevStores, ...data.content];
    });
  }, [data]);
  console.warn('data 리스트:', data);

  // 가게 추가로드 함수
  const loadMoreStores = useCallback(() => {
    if (loading || !data) return;
    if (!data.last && data.number === page) {
      console.log('가게 추가');
      setPage((prevPage) => prevPage + 1);
    }
  }, [data, page, loading]);

  // 가게 추가로드 함수의 최신 버전 저장
  const loadMoreStoresRef = useRef(loadMoreStores);
  useEffect(() => {
    loadMoreStoresRef.current = loadMoreStores;
  }, [loadMoreStores]);

  // 무한스크롤 이벤트 핸들러 등록
  useEffect(() => {
    // 스크롤 이벤트 중복 실행을 막기 위한 디바운싱 함수
    const debounce = (func, delay) => {
      let timeoutId;
      return () => {
        if (timeoutId) clearTimeout(timeoutId);
        timeoutId = setTimeout(() => {
          func();
        }, delay);
      };
    };

    // 무한스크롤 핸들러 구현
    const handleScroll = debounce(() => {
      if (
        window.innerHeight + window.scrollY >= // 사용자의 현재 스크롤 최하단 Y 좌표
        document.body.offsetHeight - 240 // 페이지의 총 높이 - 240
      ) {
        loadMoreStoresRef.current();
      }
    }, 100);

    window.addEventListener('scroll', handleScroll);

    // 컴포넌트 언마운트 시 핸들러 클린업
    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  if (error) return <ErrorContainer error={error} onRetry={refetch} />;
  if (!loading && storeList.length === 0)
    return <ErrorContainer error="등록된 가게가 존재하지 않습니다." />;

  return (
    <>
      <div className={styles.storeListContainer}>
        {storeList.map((store) => (
          <div key={store.id} className={styles.storeCard}>
            <Link to={`/store/${store.id}`}>
              <img
                className={styles.storeImage}
                src={`${store.image}`}
                alt={`${store.name} 이미지`}
              />
              <div className={styles.storeInfo}>
                <span className={styles.storeName}>{store.name}</span>
                <span className={styles.storeLocal}>{store.local}</span>
              </div>
              <div className={styles.storeStats}>
                <span className={styles.storeMeta}>
                  <IoEyeOutline />
                  &nbsp;{store.views}
                  &nbsp;&nbsp;
                  <FaHeart />
                  &nbsp;500
                </span>
                <span>
                  <FaStar /> 5.0
                </span>
              </div>
            </Link>
          </div>
        ))}
      </div>
      {loading && <LoadingContainer />}
    </>
  );
};

export default StoreList;
