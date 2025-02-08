import React, { useState } from 'react';
import Container from '../../components/container/Container'; // Container Component
import InputField from '../../components/inputField/InputField'; // InputField Component
import styles from './LoginForm.module.css';
import images from '../../assets/images'; // Image file 사용을 위한 Image Module
import { Link, useNavigate } from 'react-router-dom';
import Button from '../../components/button/Button';
import FindId from './FindId';
import FindPwd from './FindPwd';
const LoginForm = () => {
  const navigator = useNavigate();
  const [isIdModalOpen, setIdModalOpen] = useState(false); // 아이디 찾기 모달 상태
  const [isPwdModalOpen, setPwdModalOpen] = useState(false); // 비밀번호 찾기 모달 상태

  const closeIdModal = () => setIdModalOpen(false); // 아이디 찾기 모달 닫기
  const closePwdModal = () => setPwdModalOpen(false); // 비밀번호 찾기 모달 닫기
  return (
    <Container>
      <Link to="/">
        <img
          src={images.nyaminyami}
          alt="냐미냐미 로고, 홈으로 돌아가기"
          className={styles.logo}
        />
      </Link>
      <form>
        <div className={styles.inputContainer}>
          <InputField type="text" name="id" placeholder="아이디" />
          <InputField
            type="password"
            name="password"
            placeholder="비밀번호"
            customStyles={{ marginBottom: '30px' }}
          />
          <Button
            type="submit"
            onClick={(e) => {
              e.preventDefault();
              localStorage.setItem(
                'user',
                JSON.stringify({ username: 'admin', nickname: 'nickname' })
              );
              alert('로그인 성공');
              navigator('/');
            }}
          >
            로그인
          </Button>
        </div>
      </form>

      <div className={styles.socialContainer}>
        <Link>
          <img src={images.kakaoButton} alt="카카오" />
        </Link>
        <Link>
          <img src={images.googleButton} alt="구글" />
        </Link>
        <Link>
          <img src={images.naverButton} alt="네이버" />
        </Link>
      </div>
      <div className={styles.joinWrapper}>
        <p>
          아직 회원이 아니신가요?
          <Link to="/signup" className={styles.joinLink}>
            회원가입
          </Link>
        </p>
      </div>
      <div className={styles.findInfoContainer}>
        {/* 정보 찾기 Modal 컴포넌트*/}
        <FindId isOpen={isIdModalOpen} onClose={closeIdModal} />

        <FindPwd isOpen={isPwdModalOpen} onClose={closePwdModal} />
      </div>
    </Container>
  );
};

export default LoginForm;
