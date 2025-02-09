import React, { useEffect, useRef, useState } from 'react';
import Container from '../../components/container/Container';
import InputField from '../../components/inputField/InputField';
import EmailInputField from '../../components/inputField/EmailInputField';
import styles from './SignUpForm.module.css';
import images from '../../assets/images';
import { Link } from 'react-router-dom';
import Button from '../../components/button/Button';

const SignUpForm = () => {
  const [formData, setFormData] = useState({
    username: '',
    nickname: '',
    password: '',
    confirmPassword: '',
    email: '',
  }); // 폼 데이터 저장객체
  const [isEmailSent, setIsEmailSent] = useState(false); // 확인코드 이메일 전송여부
  const [isEmailVerified, setIsEmailVerified] = useState(false); // 이메일 인증여부
  const [inputVerifyCode, setInputVerifyCode] = useState(''); // 입력한 인증코드
  const [errorMessage, setErrorMessage] = useState(''); // 유효성 검사 메시지
  const [emailVerifyMessage, setEmailVerifyMessage] = useState(''); // 이메일 인증 메시지

  // 각 입력 필드 참조
  const usernameRef = useRef(null);
  const nicknameRef = useRef(null);
  const passwordRef = useRef(null);
  const confirmPasswordRef = useRef(null);

  // EmailInputField에서 이메일 변경 시 호출될 함수
  const handleEmailChange = (email) => {
    setFormData((prev) => ({ ...prev, email }));
  };

  // 모든 입력 필드에서 사용하는 onChange 핸들러
  const handleChange = (e) => {
    setFormData((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const [timeLimit, setTimeLimit] = useState(180); // 타이머 제한시간
  const [isRunning, setIsRunning] = useState(false); // 타이머 활성화 여부
  const [isExpired, setIsExpired] = useState(true); // 인증번호 만료 여부

  // 이메일로 인증번호를 전송하는 함수
  const handleVerifyEmail = () => {
    if (!formData.email) {
      setErrorMessage('이메일을 입력하세요.');
      return;
    }
    setIsEmailSent(true);
    setIsExpired(false);
    handleStartTimer();
    setEmailVerifyMessage('인증번호가 이메일로 전송되었습니다.');
    setErrorMessage('');
    console.log('인증 요청: ', formData.email);
  };

  // 입력한 인증번호가 일치하는지 확인하는 함수
  const handleCheckVerifyCode = () => {
    console.log('인증코드: ', inputVerifyCode);
    if (isExpired) {
      alert('인증번호가 만료되었습니다. 다시 인증해주세요.');
    }
    if (inputVerifyCode === '123456') {
      setErrorMessage('');
      setEmailVerifyMessage('이메일이 인증되었습니다.');
      setIsEmailVerified(true);
      setIsRunning(false);
    } else {
      setErrorMessage('인증 코드가 일치하지 않습니다.');
    }
  };

  // 타이머 시간 감소 로직
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

  // 타이머 시작/재시작 함수
  const handleStartTimer = () => {
    setTimeLimit(10);
    setIsRunning(true);
  };

  // 폼 제출 함수
  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('회원가입 데이터:', formData);

    if (!formData.username) {
      setErrorMessage('아이디를 입력하세요.');
      usernameRef.current.focus();
      return;
    }

    if (!formData.nickname) {
      setErrorMessage('닉네임을 입력하세요.');
      nicknameRef.current.focus();
      return;
    }

    if (!formData.password) {
      setErrorMessage('비밀번호를 입력하세요.');
      passwordRef.current.focus();
      return;
    }

    if (formData.password !== formData.confirmPassword) {
      setErrorMessage('비밀번호가 일치하지 않습니다.');
      confirmPasswordRef.current.focus();
      return;
    }

    if (!isEmailVerified) {
      setErrorMessage('이메일 인증이 완료되지 않았습니다.');
      return;
    }

    setErrorMessage('');
  };

  return (
    <Container height="auto">
      <Link to="/">
        <img
          src={images.nyaminyami}
          alt="냐미냐미 로고, 홈으로 돌아가기"
          className={styles.logo}
        />
      </Link>

      <h1 className={styles.intro}>
        회원가입을 위해 빈칸을 모두 작성해주세요.
      </h1>
      <form onSubmit={handleSubmit}>
        <div className={styles.signupContainer}>
          <InputField
            type="text"
            name="username"
            placeholder="아이디"
            autoComplete="username"
            ref={usernameRef}
            value={formData.username}
            onChange={handleChange}
          />
          <InputField
            type="text"
            name="nickname"
            placeholder="닉네임"
            autoComplete="off"
            ref={nicknameRef}
            value={formData.nickname}
            onChange={handleChange}
          />
          <InputField
            type="password"
            name="password"
            placeholder="비밀번호"
            autoComplete="new-password"
            ref={passwordRef}
            value={formData.password}
            onChange={handleChange}
          />
          <InputField
            type="password"
            name="confirmPassword"
            placeholder="비밀번호 확인"
            autoComplete="new-password"
            ref={confirmPasswordRef}
            value={formData.confirmPassword}
            onChange={handleChange}
            customStyles={{ marginBottom: '10px' }}
          />
          <EmailInputField
            inputVerifyCode={inputVerifyCode}
            onVerifyCodeChange={setInputVerifyCode}
            onEmailChange={handleEmailChange}
            onVerifyEmail={handleVerifyEmail}
            onVerifyCode={handleCheckVerifyCode}
            isEmailSent={isEmailSent}
            isExpired={isExpired}
            isEmailVerified={isEmailVerified}
          />

          {emailVerifyMessage && (
            <div className={styles.emailVerifyMessageWrapper}>
              <span className={styles.emailVerifyMessage}>
                {timeLimit > 0
                  ? emailVerifyMessage
                  : '인증 시간이 만료되었습니다. 다시 인증해주세요.'}
              </span>
              {isRunning && !isEmailVerified && (
                <span>
                  {Math.floor(timeLimit / 60)}:
                  {String(timeLimit % 60).padStart(2, '0')}
                </span>
              )}
            </div>
          )}

          {errorMessage && (
            <span className={styles.errorMessage}>{errorMessage}</span>
          )}

          <Button
            type="submit"
            disabled={false}
            customStyles={{ marginTop: '20px' }}
          >
            회원가입
          </Button>
        </div>
      </form>
      <div className={styles.guide}>
        <Link to="/login" className={styles.guideLink}>
          로그인 화면으로 돌아가기
        </Link>
      </div>
    </Container>
  );
};

export default SignUpForm;
