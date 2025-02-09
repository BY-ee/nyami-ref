import React, { useEffect, useRef, useState } from 'react';
import Container from '../../components/container/Container';
import InputField from '../../components/inputField/InputField';
import EmailInputField from '../../components/inputField/EmailInputField';
import styles from './SignUpForm.module.css';
import images from '../../assets/images';
import { Link } from 'react-router-dom';
import Button from '../../components/button/Button';
import useEmailVerification from '../../hooks/useEmailVerification';

const SignUpForm = () => {
  const [formData, setFormData] = useState({
    username: '',
    nickname: '',
    password: '',
    confirmPassword: '',
  }); // 폼 데이터 저장객체
  const {
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
  } = useEmailVerification();
  const [errorMessage, setErrorMessage] = useState(''); // 유효성 검사 메시지

  // 각 입력 필드 참조
  const usernameRef = useRef(null);
  const nicknameRef = useRef(null);
  const passwordRef = useRef(null);
  const confirmPasswordRef = useRef(null);

  // 모든 입력 필드에서 사용하는 onChange 핸들러
  const handleChange = (e) => {
    setFormData((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  // 폼 제출 함수
  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('회원가입 데이터:', { ...formData, email });

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
            onVerifyCode={handleVerifyCode}
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
