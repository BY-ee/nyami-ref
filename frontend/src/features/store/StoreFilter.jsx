import { useRef, useState } from 'react';
import CategoryDropdown from '../../components/dropdown/CategoryDropdown';
import TagContainer from '../../components/tag/TagContainer';
import styles from './StoreFilter.module.css';
import Select from '../../components/select/Select';

const StoreFilter = ({
  local,
  foodCategory,
  theme,
  sort,
  onLocalChange,
  onFoodCategoryChange,
  onThemeChange,
  onSortChange,
}) => {
  const [isLocalOpen, setIsLocalOpen] = useState(false); // 지역 드롭다운 열림 상태
  const [isFoodCategoryOpen, setIsFoodCategoryOpen] = useState(false); // 업종 드롭다운 열림 상태
  const [isThemeOpen, setIsThemeOpen] = useState(false); // 테마 드롭다운 열림 상태

  const localDropdownRef = useRef(null); // 지역 드롭다운 참조
  const localButtonRef = useRef(null); // 지역 버튼 참조

  const foodCategoryDropdownRef = useRef(null); // 업종 드롭다운 참조
  const foodCategoryButtonRef = useRef(null); // 업종 버튼 참조

  const themeDropdownRef = useRef(null); // 테마 드롭다운 참조
  const themeButtonRef = useRef(null); // 테마 버튼 참조

  const localData = [
    { id: 1, content: '강남구' },
    { id: 2, content: '서초구' },
    { id: 3, content: '송파구' },
    { id: 4, content: '마포구' },
    { id: 5, content: '종로구' },
    { id: 6, content: '동대문구' },
  ];

  const foodCategoryData = [
    { id: 1, content: '한식' },
    { id: 2, content: '중식' },
    { id: 3, content: '일식' },
    { id: 4, content: '양식' },
    { id: 5, content: '분식' },
    { id: 6, content: '기타' },
  ];

  const themeData = [
    { id: 1, content: '혼밥하기 좋은' },
    { id: 2, content: '회식' },
    { id: 3, content: '데이트 코스' },
    { id: 4, content: '분위기 있는' },
  ];

  const sortOptions = [
    { id: 1, value: 'createdAt', label: '최신순' },
    { id: 2, value: 'views', label: '조회순' },
  ];

  return (
    <>
      <div className={styles.filterContainer}>
        <div className={styles.categoryButtonWrapper}>
          <CategoryDropdown
            label="지역"
            data={localData}
            isOpen={isLocalOpen}
            onClose={() => setIsLocalOpen(!isLocalOpen)}
            dropdownRef={localDropdownRef}
            buttonRef={localButtonRef}
            onItemClick={(content) => {
              onLocalChange(content);
            }}
          />

          <CategoryDropdown
            label="업종"
            data={foodCategoryData}
            isOpen={isFoodCategoryOpen}
            onClose={() => setIsFoodCategoryOpen(!isFoodCategoryOpen)}
            dropdownRef={foodCategoryDropdownRef}
            buttonRef={foodCategoryButtonRef}
            onItemClick={(content) => {
              onFoodCategoryChange(content);
            }}
          />

          <CategoryDropdown
            label="테마"
            data={themeData}
            isOpen={isThemeOpen}
            onClose={() => setIsThemeOpen(!isThemeOpen)}
            dropdownRef={themeDropdownRef}
            buttonRef={themeButtonRef}
            onItemClick={(content) => {
              onThemeChange(content);
            }}
          />
        </div>

        <Select
          options={sortOptions}
          value={sort}
          onChange={onSortChange}
          placeholder="정렬"
          customStyles={{ width: '90px' }}
        />
      </div>
      <TagContainer
        tags={[local, foodCategory, theme]}
        onLocalChange={onLocalChange}
        onFoodCategoryChange={onFoodCategoryChange}
        onThemeChange={onThemeChange}
      />
    </>
  );
};

export default StoreFilter;
