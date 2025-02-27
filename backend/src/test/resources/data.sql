INSERT INTO local (local) VALUES
('강남구'),
('서초구'),
('송파구'),
('마포구'),
('종로구'),
('동대문구');

INSERT INTO food_category (food_category) VALUES
('한식'),
('중식'),
('일식'),
('양식'),
('분식'),
('기타');

INSERT INTO theme (theme) VALUES
('혼밥하기 좋은'),
('회식'),
('데이트 코스'),
('분위기 있는');

INSERT INTO store (local_id, food_category_id, theme_id, name, address, detail_address, tel, image, x, y, description, views) VALUES
(1, 1, 1, '강남 한식당', '서울특별시 강남구 테헤란로 123', '빌딩 5층', '02-111-1111', '/images/store/1/store_1.jpg', 127.027, 37.497, '정갈한 한식 메뉴를 즐길 수 있는 곳입니다.', 100),
(2, 2, 2, '서초 중식당', '서울특별시 서초구 반포대로 456', '빌딩 2층', '02-222-2222', '/images/store/2/store_2.jpg', 127.014, 37.483, '다양한 중식을 즐길 수 있는 회식 장소입니다.', 200),
(3, 3, 3, '송파 일식당', '서울특별시 송파구 올림픽로 789', '상가 1층', '02-333-3333', '/images/store/3/store_3.jpg', 127.120, 37.510, '데이트에 적합한 일식 전문점입니다.', 150),
(4, 4, 4, '마포 양식당', '서울특별시 마포구 홍익로 101', '건물 1층', '02-444-4444', '/images/store/4/store_4.jpg', 126.921, 37.556, '분위기 있는 양식 레스토랑입니다.', 120),
(5, 5, 1, '종로 분식집', '서울특별시 종로구 세종대로 202', '상가 B1', '02-555-5555', '/images/store/5/store_5.jpg', 126.978, 37.572, '혼자서도 즐길 수 있는 분식 맛집입니다.', 80),
(6, 6, 2, '동대문 기타식당', '서울특별시 동대문구 장한로 333', '빌딩 3층', '02-666-6666', '/images/store/6/store_6.jpg', 127.067, 37.577, '다양한 메뉴를 제공하는 회식 추천 장소입니다.', 300),
(1, 3, 3, '강남 초밥집', '서울특별시 강남구 도산대로 99', '건물 1층', '02-777-7777', '/images/store/7/store_7.jpg', 127.040, 37.514, '데이트에 어울리는 신선한 초밥 전문점입니다.', 180),
(2, 5, 4, '서초 떡볶이집', '서울특별시 서초구 서초대로 678', '상가 2층', '02-888-8888', '/images/store/8/store_8.jpg', 127.010, 37.491, '분위기 좋은 분식 카페입니다.', 50),
(3, 2, 1, '송파 탕수육집', '서울특별시 송파구 송파대로 55', '건물 3층', '02-999-9999', '/images/store/9/store_9.jpg', 127.105, 37.505, '혼밥 가능한 탕수육 전문점입니다.', 70),
(4, 4, 2, '마포 스테이크하우스', '서울특별시 마포구 마포대로 123', '빌딩 5층', '02-101-0101', '/images/store/10/store_10.jpg', 126.950, 37.548, '회식에 적합한 스테이크 전문점입니다.', 250);


INSERT INTO menu (store_id, name, price, description, image) VALUES
(1, '파스타', 12000, '신선한 재료로 만든 정통 이탈리안 파스타', '/images/menu/1/store_1_menu_1.jpg'),
(1, '스테이크', 25000, '최상급 등심을 사용한 육즙 가득한 스테이크', '/images/menu/1/store_1_menu_2.jpg'),
(1, '초밥', 18000, '신선한 해산물과 정갈한 밥으로 만든 초밥 세트', '/images/menu/1/store_1_menu_3.jpg'),
(1, '라면', 8000, '진한 육수와 쫄깃한 면발의 일본식 라면', '/images/menu/1/store_1_menu_4.jpg'),

(2, '파스타', 12000, '신선한 재료로 만든 정통 이탈리안 파스타', '/images/menu/2/store_2_menu_1.jpg'),
(2, '스테이크', 25000, '최상급 등심을 사용한 육즙 가득한 스테이크', '/images/menu/2/store_2_menu_2.jpg'),
(2, '초밥', 18000, '신선한 해산물과 정갈한 밥으로 만든 초밥 세트', '/images/menu/2/store_2_menu_3.jpg'),
(2, '라면', 8000, '진한 육수와 쫄깃한 면발의 일본식 라면', '/images/menu/2/store_2_menu_4.jpg'),

(3, '파스타', 12000, '신선한 재료로 만든 정통 이탈리안 파스타', '/images/menu/3/store_3_menu_1.jpg'),
(3, '스테이크', 25000, '최상급 등심을 사용한 육즙 가득한 스테이크', '/images/menu/3/store_3_menu_2.jpg'),
(3, '초밥', 18000, '신선한 해산물과 정갈한 밥으로 만든 초밥 세트', '/images/menu/3/store_3_menu_3.jpg'),
(3, '라면', 8000, '진한 육수와 쫄깃한 면발의 일본식 라면', '/images/menu/3/store_3_menu_4.jpg'),

(4, '파스타', 12000, '신선한 재료로 만든 정통 이탈리안 파스타', '/images/menu/4/store_4_menu_1.jpg'),
(4, '스테이크', 25000, '최상급 등심을 사용한 육즙 가득한 스테이크', '/images/menu/4/store_4_menu_2.jpg'),
(4, '초밥', 18000, '신선한 해산물과 정갈한 밥으로 만든 초밥 세트', '/images/menu/4/store_4_menu_3.jpg'),
(4, '라면', 8000, '진한 육수와 쫄깃한 면발의 일본식 라면', '/images/menu/4/store_4_menu_4.jpg'),

(5, '파스타', 12000, '신선한 재료로 만든 정통 이탈리안 파스타', '/images/menu/5/store_5_menu_1.jpg'),
(5, '스테이크', 25000, '최상급 등심을 사용한 육즙 가득한 스테이크', '/images/menu/5/store_5_menu_2.jpg'),
(5, '초밥', 18000, '신선한 해산물과 정갈한 밥으로 만든 초밥 세트', '/images/menu/5/store_5_menu_3.jpg'),
(5, '라면', 8000, '진한 육수와 쫄깃한 면발의 일본식 라면', '/images/menu/5/store_5_menu_4.jpg'),

(6, '파스타', 12000, '신선한 재료로 만든 정통 이탈리안 파스타', '/images/menu/6/store_6_menu_1.jpg'),
(6, '스테이크', 25000, '최상급 등심을 사용한 육즙 가득한 스테이크', '/images/menu/6/store_6_menu_2.jpg'),
(6, '초밥', 18000, '신선한 해산물과 정갈한 밥으로 만든 초밥 세트', '/images/menu/6/store_6_menu_3.jpg'),
(6, '라면', 8000, '진한 육수와 쫄깃한 면발의 일본식 라면', '/images/menu/6/store_6_menu_4.jpg'),

(7, '파스타', 12000, '신선한 재료로 만든 정통 이탈리안 파스타', '/images/menu/7/store_7_menu_1.jpg'),
(7, '스테이크', 25000, '최상급 등심을 사용한 육즙 가득한 스테이크', '/images/menu/7/store_7_menu_2.jpg'),
(7, '초밥', 18000, '신선한 해산물과 정갈한 밥으로 만든 초밥 세트', '/images/menu/7/store_7_menu_3.jpg'),
(7, '라면', 8000, '진한 육수와 쫄깃한 면발의 일본식 라면', '/images/menu/7/store_7_menu_4.jpg'),

(8, '파스타', 12000, '신선한 재료로 만든 정통 이탈리안 파스타', '/images/menu/8/store_8_menu_1.jpg'),
(8, '스테이크', 25000, '최상급 등심을 사용한 육즙 가득한 스테이크', '/images/menu/8/store_8_menu_2.jpg'),
(8, '초밥', 18000, '신선한 해산물과 정갈한 밥으로 만든 초밥 세트', '/images/menu/8/store_8_menu_3.jpg'),
(8, '라면', 8000, '진한 육수와 쫄깃한 면발의 일본식 라면', '/images/menu/8/store_8_menu_4.jpg'),

(9, '파스타', 12000, '신선한 재료로 만든 정통 이탈리안 파스타', '/images/menu/9/store_9_menu_1.jpg'),
(9, '스테이크', 25000, '최상급 등심을 사용한 육즙 가득한 스테이크', '/images/menu/9/store_9_menu_2.jpg'),
(9, '초밥', 18000, '신선한 해산물과 정갈한 밥으로 만든 초밥 세트', '/images/menu/9/store_9_menu_3.jpg'),
(9, '라면', 8000, '진한 육수와 쫄깃한 면발의 일본식 라면', '/images/menu/9/store_9_menu_4.jpg'),

(10, '파스타', 12000, '신선한 재료로 만든 정통 이탈리안 파스타', '/images/menu/10/store_10_menu_1.jpg'),
(10, '스테이크', 25000, '최상급 등심을 사용한 육즙 가득한 스테이크', '/images/menu/10/store_10_menu_2.jpg'),
(10, '초밥', 18000, '신선한 해산물과 정갈한 밥으로 만든 초밥 세트', '/images/menu/10/store_10_menu_3.jpg'),
(10, '라면', 8000, '진한 육수와 쫄깃한 면발의 일본식 라면', '/images/menu/10/store_10_menu_4.jpg');

INSERT INTO users (username, password, naver_id, google_id, kakao_id, email, nickname, profile_image, introduction) VALUES
('test', 'password123!', NULL, NULL, NULL, 'test@example.com', 'nickname1', 'https://example.com/profile1.jpg', 'sns 연동 데이터가 없는 유저'),
('test2', 'password123!', 'test2_naver', NULL, NULL, 'test2@example.com', 'nickname2', 'https://example.com/profile2.jpg', '네이버 연동만 되어 있는 유저'),
('test3', 'password123!', NULL, 'test3_google', NULL, 'test3@example.com', 'nickname3', 'https://example.com/profile3.jpg', '구글 연동만 되어 있는 유저'),
('test4', 'password123!', NULL, NULL, 'test4_kakao', 'test4@example.com', 'nickname4', 'https://example.com/profile4.jpg', '카카오 연동만 되어 있는 유저'),
('test5', 'password123!', 'test5_naver', 'test5_google', 'test5_kakao', 'test5@example.com', 'nickname5', 'https://example.com/profile5.jpg', '모든 플랫폼에 연동되어 있는 유저');
