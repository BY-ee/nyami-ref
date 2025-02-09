import styles from './Button.module.css';

/**
 * 공통 버튼 컴포넌트입니다.
 *
 * @param {React.ReactNode} children - 버튼에 표시할 데이터
 * @param {'button' | 'submit'} [type='button'] - 버튼 타입
 * @param {Function} onClick - 버튼 클릭 시 실행할 함수
 * @param {boolean} [disabled=false] - 버튼 활성화 여부
 * @param {object} customStyles - 커스텀 스타일
 * @param {object} props - 기타 HTML 속성
 * @returns {JSX.Element} 버튼 컴포넌트
 */
const Button = ({
  children,
  type = 'button',
  onClick,
  disabled = false,
  customStyles = {},
  ...props
}) => {
  return (
    <button
      className={`${styles.btn}`}
      type={type}
      onClick={onClick}
      disabled={disabled}
      style={customStyles}
      {...props}
    >
      {children}
    </button>
  );
};

export default Button;
