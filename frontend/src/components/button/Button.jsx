import styles from './Button.module.css';

/**
 * 공통 버튼 컴포넌트입니다.
 *
 * @param {React.ReactNode} children - 버튼에 표시할 데이터
 * @param {Function} onClick - 버튼 클릭 시 실행할 함수
 * @param {'small' | 'medium' | 'large'} [size='medium'] - 버튼 크기
 * @param {boolean} [disabled=false] - 버튼 활성화 여부
 * @param {object} props - 기타 HTML 속성
 * @returns {JSX.Element} 버튼 컴포넌트
 */
const Button = ({
  children,
  onClick,
  disabled = false,
  customStyles = {},
  ...props
}) => {
  return (
    <button
      className={`${styles.btn} ${disabled ? styles.disabled : ''}`}
      type="button"
      onClick={!disabled && onClick ? onClick : undefined}
      disabled={disabled}
      style={customStyles}
      {...props}
    >
      {children}
    </button>
  );
};

export default Button;
