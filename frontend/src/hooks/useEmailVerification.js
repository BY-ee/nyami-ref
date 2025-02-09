import { useEffect, useState } from 'react';

/**
 * 이메일 인증 관련 로직을 관리하는 커스텀 훅
 * @returns {{
 *   email: string,
 *   isEmailSent: boolean,
 *   isEmailVerified: boolean,
 *   inputVerifyCode: string,
 *   emailVerifyMessage: string,
 *   timeLimit: number,
 *   isRunning: boolean,
 *   isExpired: boolean,
 *   handleEmailChange: (email: string) => void,
 *   handleVerifyEmail: () => void,
 *   handleVerifyCode: () => void,
 *   setInputVerifyCode: (code: string) => void
 * }} - 이메일 인증 관련 상태 및 함수 반환
 */
const useEmailVerification = () => {
  /** @type {string} 이메일 입력 값 */
  const [email, setEmail] = useState('');

  /** @type {boolean} 이메일 인증번호 전송 여부 */
  const [isEmailSent, setIsEmailSent] = useState(false);

  /** @type {boolean} 이메일 인증 여부 */
  const [isEmailVerified, setIsEmailVerified] = useState(false);

  /** @type {string} 입력된 인증번호 */
  const [inputVerifyCode, setInputVerifyCode] = useState('');

  /** @type {string} 이메일 인증 메시지 */
  const [emailVerifyMessage, setEmailVerifyMessage] = useState('');

  /** @type {number} 남은 타이머 시간 (초) */
  const [timeLimit, setTimeLimit] = useState(180);

  /** @type {boolean} 타이머 실행 여부 */
  const [isRunning, setIsRunning] = useState(false);

  /** @type {boolean} 인증번호 만료 여부 */
  const [isExpired, setIsExpired] = useState(true);

  // 이메일 입력 핸들러
  const handleEmailChange = (newEmail) => {
    setEmail(newEmail);
  };

  // 이메일로 인증번호 전송
  const handleVerifyEmail = () => {
    if (!email) {
      setEmailVerifyMessage('이메일을 입력하세요.');
      return;
    }
    setIsEmailSent(true);
    setIsExpired(false);
    handleStartTimer();
    setEmailVerifyMessage('인증번호가 이메일로 전송되었습니다.');
    console.log('인증 요청: ', email);
  };

  // 인증번호 확인
  const handleVerifyCode = () => {
    if (isExpired) {
      alert('인증번호가 만료되었습니다. 다시 인증해주세요.');
      return;
    }

    if (inputVerifyCode === '123456') {
      setEmailVerifyMessage('이메일이 인증되었습니다.');
      setIsEmailVerified(true);
      setIsRunning(false);
    } else {
      setEmailVerifyMessage('인증 코드가 일치하지 않습니다.');
    }
  };

  // 타이머 관리 로직
  useEffect(() => {
    if (!isRunning || timeLimit <= 0 || isEmailVerified) {
      return;
    }

    const timer = setInterval(() => {
      setTimeLimit((prev) => {
        if (prev <= 1) {
          clearInterval(timer);
          setIsRunning(false);
          setIsExpired(true);
          return 0;
        }
        return prev - 1;
      });
    }, 1000);

    return () => clearInterval(timer);
  }, [isRunning, timeLimit, isEmailVerified]);

  // 타이머 시작/재시작
  const handleStartTimer = () => {
    setTimeLimit(180);
    setIsRunning(true);
  };

  return {
    email,
    isEmailSent,
    isEmailVerified,
    inputVerifyCode,
    emailVerifyMessage,
    timeLimit,
    isRunning,
    isExpired,
    handleEmailChange,
    handleVerifyEmail,
    handleVerifyCode,
    setInputVerifyCode,
  };
};

export default useEmailVerification;
