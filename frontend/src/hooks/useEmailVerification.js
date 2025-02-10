import { useEffect, useState } from 'react';

/**
 * 이메일 인증 관련 로직을 관리하는 커스텀 훅
 * @returns {{
 *   email: string,
 *   isEmailValid: boolean,
 *   isEmailSent: boolean,
 *   isEmailVerified: boolean,
 *   isExpired: boolean,
 *   timeLimit: number,
 *   handleEmailInput: (email: string) => void,
 *   sendEmail: () => string,
 *   handleVerifyCode: (code: string) => string,
 *   resetState: () => void
 * }} - 이메일 인증 관련 상태 및 함수 반환
 */
const useEmailVerification = () => {
  const [email, setEmail] = useState('');
  const [isEmailValid, setIsEmailValid] = useState(false);
  const [isEmailSent, setIsEmailSent] = useState(false);
  const [isEmailVerified, setIsEmailVerified] = useState(false);
  const [timeLimit, setTimeLimit] = useState(0);
  const isExpired = timeLimit === 0;

  // 이메일 변경 핸들러
  const handleEmailInput = (newEmail) => {
    setEmail(newEmail);
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    setIsEmailValid(emailPattern.test(newEmail));
  };

  // 이메일 인증번호 요청
  const sendEmail = () => {
    if (!email) return '이메일을 입력하세요.';
    if (!isEmailValid) return '잘못된 이메일 형식입니다.';

    setIsEmailSent(true);
    setTimeLimit(10);
    console.log('인증 요청: ', email);
    return '인증번호가 이메일로 전송되었습니다.';
  };

  // 인증번호 확인
  const handleVerifyCode = (code) => {
    if (isExpired) return '인증번호가 만료되었습니다. 다시 인증해주세요.';
    if (code === '123456') {
      setIsEmailVerified(true);
      return '이메일이 인증되었습니다.';
    }
    return '인증 코드가 일치하지 않습니다.';
  };

  // 타이머 관리
  useEffect(() => {
    if (timeLimit <= 0 || !isEmailSent || isEmailVerified) return;

    const timer = setInterval(() => {
      setTimeLimit((prev) => (prev > 0 ? prev - 1 : 0));
    }, 1000);

    return () => clearInterval(timer);
  }, [timeLimit, isEmailSent, isEmailVerified]);

  // 상태 초기화
  const resetState = () => {
    setEmail('');
    setIsEmailSent(false);
    setIsEmailVerified(false);
    setTimeLimit(0);
  };

  return {
    email,
    isEmailValid,
    isEmailSent,
    isEmailVerified,
    isExpired,
    timeLimit,
    handleEmailInput,
    sendEmail,
    handleVerifyCode,
    resetState,
  };
};

export default useEmailVerification;
