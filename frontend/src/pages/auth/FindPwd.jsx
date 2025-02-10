import React, { useEffect, useState } from 'react';
import BasicModal from '../../components/modal/BasicModal';
import InputField from '../../components/inputField/InputField';
import EmailInputField from '../../components/inputField/EmailInputField';
import useEmailVerification from '../../hooks/useEmailVerification';
import styles from './FindPwd.module.css';
import ModalWithBackdrop from '../../components/modal/ModalWithBackdrop';

const FindPwd = ({ isOpen, onClose }) => {
  const {
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
  } = useEmailVerification();
  const [emailVerifyMessage, setEmailVerifyMessage] = useState('');
  const [verificationCode, setVerificationCode] = useState('');

  // 이메일 전송 로직 실행 후 메시지 설정
  const handleSendEmail = () => {
    const message = sendEmail();
    setEmailVerifyMessage(message);
  };

  // 인증코드 확인 로직 실행 후 메시지 설정
  const verifyCode = () => {
    const message = handleVerifyCode(verificationCode);
    setEmailVerifyMessage(message);
  };

  // 인증 만료 시 메시지 설정
  useEffect(() => {
    if (isExpired && isEmailSent) {
      setEmailVerifyMessage('인증번호가 만료되었습니다. 다시 인증해주세요.');
    }
  }, [isExpired]);

  // 이메일 인증 상태 초기화
  useEffect(() => {
    if (!isOpen) {
      setEmailVerifyMessage('');
      resetState();
    }
  }, [isOpen]);

  return (
    <ModalWithBackdrop isOpen={isOpen} onClose={onClose} width={500}>
      <div className={styles.contentWrapper}>
        <h3 className={styles.title}>비밀번호 찾기</h3>
        <p className={styles.intro}>
          회원가입에 인증하신 이메일로 비밀번호 재설정 링크가 발송됩니다.
        </p>

        <InputField
          type="text"
          name="id"
          placeholder="아이디"
          autoComplete="username"
          customStyles={{ height: '30px' }}
        />

        <EmailInputField
          verificationCode={verificationCode}
          onVerificationCode={setVerificationCode}
          onVerifyCode={verifyCode}
          onEmailChange={handleEmailInput}
          onSendEmail={handleSendEmail}
          isEmailSent={isEmailSent}
          isExpired={isExpired}
          isEmailVerified={isEmailVerified}
        />

        {emailVerifyMessage && (
          <div className={styles.emailVerifyMessageWrapper}>
            <span className={styles.emailVerifyMessage}>
              {emailVerifyMessage}
            </span>
            {!isEmailVerified && timeLimit > 0 && (
              <span>
                {Math.floor(timeLimit / 60)}:
                {String(timeLimit % 60).padStart(2, '0')}
              </span>
            )}
          </div>
        )}
      </div>
    </ModalWithBackdrop>
  );
};

export default FindPwd;
