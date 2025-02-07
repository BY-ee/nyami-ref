import { useEffect, useState } from 'react';
import { FaHeart, FaRegHeart } from 'react-icons/fa';
import { IoIosArrowForward } from 'react-icons/io';
import { PiShareBold } from 'react-icons/pi';
import { useParams } from 'react-router-dom';
import Slider from 'react-slick';
import ErrorContainer from '../../components/container/ErrorContainer';
import LoadingContainer from '../../components/container/LoadingContainer';
import useFetch from '../../hooks/useFetch';
import styles from './StoreDetail.module.css';
import { IoEyeOutline } from 'react-icons/io5';

const StoreDetail = () => {
  const [isLiked, setIsLiked] = useState(false); // 좋아요 상태
  const [likeCount, setLikeCount] = useState(500); // 좋아요 개수
  const { storeId } = useParams(); // url의 id 값 저장
  const {
    data: store,
    loading,
    error,
    refetch,
  } = useFetch(`/api/stores/${storeId}`);

  console.warn(store);

  useEffect(() => {
    const timer = setTimeout(() => {
      increaseViewCount(storeId);
    }, 3000); // 3초 후 조회수 증가

    return () => clearTimeout(timer);
  }, [storeId]);

  // 조회수 증가 API
  function increaseViewCount(storeId) {
    fetch(`http://localhost:8090/api/stores/${storeId}/view`, {
      method: 'PATCH',
    })
      .then(() => {
        console.log('조회수 증가 성공');
      })
      .catch(() => {
        console.error('조회수 증가 실패');
      });
  }

  // 좋아요 상태를 업데이트하는 함수
  function updateLikeState() {
    if (isLiked) {
      setLikeCount((prev) => prev - 1);
    } else {
      setLikeCount((prev) => prev + 1);
    }
    setIsLiked((prev) => !prev);
  }

  // 슬라이더 속성
  const storeSettings = {
    infinite: true,
    slidesToShow: 2,
    slidesToScroll: 1,
    speed: 400,
  };

  const menuSettings = {
    infinite: false,
    slidesToShow: 2,
    slidesToScroll: 1,
    speed: 400,
  };

  // 가게 이미지 배열
  const storeImageList = [
    {
      id: 1,
      imageUrl:
        'https://react-slick.neostack.com/img/react-slick/abstract01.jpg',
    },
    {
      id: 2,
      imageUrl:
        'https://react-slick.neostack.com/img/react-slick/abstract02.jpg',
    },
  ];

  if (loading) return <LoadingContainer />;
  if (error) return <ErrorContainer error={error} onRetry={refetch} />;
  if (!store) return <ErrorContainer error="해당 가게가 존재하지 않습니다." />;

  return (
    <>
      {/* breadcrumb */}
      <div className={styles.breadcrumb}>
        <span>{store.local}</span>
        <span>
          <IoIosArrowForward />
        </span>
        <span>{store.foodCategory}</span>
        <span>
          <IoIosArrowForward />
        </span>
        <span>{store.theme}</span>
        <span>
          <IoIosArrowForward />
        </span>
        <span>{store.name}</span>
      </div>

      {/* 가게 컨테이너 */}
      <div className={styles.storeContainer}>
        {/* 가게 제목 섹션 */}
        <div className={styles.titleWrapper}>
          <h2 className={styles.title}>{store.name}</h2>
          <div className={styles.action}>
            <div className={styles.likeBtn} onClick={updateLikeState}>
              {isLiked ? (
                <FaHeart size="25" color="#ff8484" />
              ) : (
                <FaRegHeart size="25" />
              )}
            </div>
            <div className={styles.shareBtn}>
              <PiShareBold size="25" />
            </div>
          </div>
        </div>
        <div className={styles.storeStats}>
          <span className={styles.storeMeta}>
            <IoEyeOutline />
            &nbsp;{store.views}
            &nbsp;&nbsp;
            <FaHeart />
            &nbsp;{likeCount}
          </span>
        </div>

        {/* 가게 사진 슬라이더 */}
        <div className={styles.imageSliderWrapper}>
          <Slider {...storeSettings}>
            <div className={styles.storeImage}>
              <img src={store.image} alt={`가게 사진`} />
            </div>
            {storeImageList.map((storeImage) => (
              <div key={storeImage.id} className={styles.storeImage}>
                <img
                  src={storeImage.imageUrl}
                  alt={`가게 사진 ${storeImage.id}`}
                />
              </div>
            ))}
          </Slider>
        </div>

        <div className={styles.line}></div>

        {/* 가게 상세 파트 */}
        <div className={styles.infoWrapper}>
          <div className={styles.info}>
            <div className={styles.infoTitle}>주소</div>
            <p className={styles.infoContent}>
              {store.address}, {store.detailAddress}
            </p>
          </div>
          <div className={styles.info}>
            <div className={styles.infoTitle}>전화번호</div>
            <p className={styles.infoContent}>{store.tel || '-'}</p>
          </div>
          <div className={styles.info}>
            <div className={styles.infoTitle}>영업시간</div>
            <p className={styles.infoContent}>미정</p>
          </div>
          <p className={styles.description}>{store.description}</p>
        </div>

        {/* 메뉴 사진 슬라이더 */}
        {store.menus.length > 0 && (
          <div className={styles.imageSliderWrapper}>
            <Slider {...menuSettings}>
              {store.menus.map((menu) => (
                <div key={menu.id}>
                  <img
                    src={menu.image}
                    className={styles.menuImage}
                    alt={`메뉴 ${menu.id}`}
                  />
                </div>
              ))}
            </Slider>
          </div>
        )}

        <div className={styles.line}></div>

        {/* 지도 */}
        <div className={styles.mapContainer}>Map</div>
      </div>
    </>
  );
};

export default StoreDetail;
