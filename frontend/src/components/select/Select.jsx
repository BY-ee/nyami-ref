import styles from './Select.module.css';

/**
 * 재사용 가능한 Select 컴포넌트
 * @param {Array<{ value: string, label: string }>} options - 옵션 배열 (객체 배열)
 * @param {string} value - 현재 선택된 값
 * @param {Function} onChange - 값이 변경될 때 호출되는 함수
 * @param {string} [placeholder='선택하세요'] - 기본 안내 문구
 * @param {boolean} [disabled=false] - 비활성화 여부
 * @param {boolean} [showValue=false] - label 대신 value 값을 표시할 지 여부
 * @param {Object} [customStyles={}] - 추가적인 스타일
 */
const Select = ({
  options,
  value,
  onChange,
  placeholder = '선택하세요',
  disabled = false,
  showValue = false,
  customStyles = {},
}) => {
  //
  const selectedOption = options.find((option) => option.value === value);
  const displayText = showValue ? value : selectedOption?.label;

  return (
    <select
      className={styles.select}
      value={value}
      onChange={(e) => onChange(e.target.value)}
      disabled={disabled}
      style={customStyles}
    >
      <option value="" disabled>
        {placeholder}
      </option>

      {value && (
        <option value={value} hidden>
          {displayText}
        </option>
      )}

      {options &&
        options.map((option) => (
          <option key={option.value} value={option.value}>
            {option.label}
          </option>
        ))}
    </select>
  );
};

export default Select;
