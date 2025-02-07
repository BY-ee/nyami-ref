import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';

const MAX_SCROLL_HISTORY = 10; // 스크롤 위치를 저장할 페이지 개수

const ScrollManager = () => {
  const { pathname } = useLocation();
  const [scrollPositions, setScrollPositions] = useState({}); // 페이지 별 스크롤 위치 저장
  const [isBack, setIsBack] = useState(false); // 페이지 뒤로가기 여부 확인

  useEffect(() => {
    const handlePopState = () => {
      setIsBack(true);
    };

    window.addEventListener('popstate', handlePopState);

    return () => {
      window.removeEventListener('popstate', handlePopState);
    };
  }, []);

  useEffect(() => {
    setScrollPositions((prev) => {
      // 이전 경로 정보에 현재 경로를 추가
      const updatedPathData = { ...prev, [pathname]: window.scrollY };
      const keys = Object.keys(updatedPathData);

      // 저장된 데이터 개수가 초과되면 가장 오래된 데이터 삭제
      if (keys.length > MAX_SCROLL_HISTORY) {
        const { [keys[0]]: _, ...rest } = updatedPathData;
        return rest;
      }

      return updatedPathData;
    });

    if (isBack && scrollPositions[pathname] !== undefined) {
      // 뒤로가기인 경우 이전 스크롤 위치로 복원
      window.scrollTo(0, scrollPositions[pathname]);
    } else {
      // 링크 클릭인 경우 페이지의 맨 위로 이동
      window.scrollTo(0, 0);
    }

    setIsBack(false);
  }, [pathname]);

  return null;
};

export default ScrollManager;
