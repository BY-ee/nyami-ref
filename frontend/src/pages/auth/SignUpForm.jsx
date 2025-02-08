import React from 'react';
import Container from '../../components/container/Container';
import InputField from '../../components/inputField/InputField';
import EmailInputField from '../../components/inputField/EmailInputField';
import styles from './SignUpForm.module.css';
import images from '../../assets/images';
import { Link } from 'react-router-dom';
import Button from '../../components/button/Button';

const SignUpForm = () => {
  const handleEmailChange = (email) => {
    console.log('Complete Email:', email); // 최종 이메일 출력
  };

  return (
    <Container height="600px">
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
      <form>
        <div className={styles.signupContainer}>
          <InputField type="text" name="username" placeholder="아이디" />
          <InputField type="text" name="nickname" placeholder="닉네임" />
          <InputField type="password" name="password" placeholder="비밀번호" />
          <InputField
            type="password"
            name="confirmPassword"
            placeholder="비밀번호 확인"
          />
          <EmailInputField onEmailChange={handleEmailChange} />
          <Button
            type="submit"
            disabled={false}
            customStyles={{ marginTop: '30px' }}
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
