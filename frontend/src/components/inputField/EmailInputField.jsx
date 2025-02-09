import React, { useEffect, useState } from 'react';
import InputField from '../inputField/InputField';
import styles from './EmailInputField.module.css';
import Button from '../button/Button';
import Select from '../select/Select';
const EmailInputField = ({
  inputVerifyCode,
  onVerifyCodeChange,
  onEmailChange,
  onVerifyEmail,
  onVerifyCode,
  isEmailSent,
  isExpired,
  isEmailVerified,
}) => {
  const [emailId, setEmailId] = useState('');
  const [emailDomain, setEmailDomain] = useState('');
  const [isCustomDomain, setIsCustomDomain] = useState(false); // 직접 입력 활성화 여부

  // 이메일 아이디, 도메인을 상위 컴포넌트로 전달
  useEffect(() => {
    if (emailId && emailDomain) {
      onEmailChange(`${emailId}@${emailDomain}`);
    } else {
      onEmailChange('');
    }
  }, [emailId, emailDomain]);

  // 이메일 아이디 변경
  const handleEmailIdChange = (e) => {
    setEmailId(e.target.value);
  };

  // 이메일 도메인 변경
  const handleEmailDomainChange = (value) => {
    if (value === '직접 입력') {
      setIsCustomDomain(true);
      setEmailDomain(''); // 직접 입력 활성화
    } else {
      setIsCustomDomain(false);
      setEmailDomain(value); // 선택한 도메인 설정
    }
  };

  const handleCustomDomainChange = (e) => {
    setEmailDomain(e.target.value);
  };

  const handleVerifyCodeChange = (e) => {
    onVerifyCodeChange(e.target.value);
  };

  // 이메일 도메인 선택 옵션 (컴포넌트 내부 상수로 정의)
  const emailOptions = [
    { value: 'gmail.com', label: 'Gmail' },
    { value: 'naver.com', label: 'Naver' },
    { value: 'kakao.com', label: 'Kakao' },
    { value: 'hanmail.com', label: 'Hanmail' },
    { value: '직접 입력', label: '직접 입력' },
  ];
  return (
    <>
      <div className={styles.emailInputContainer}>
        <div className={styles.emailInputWrapper}>
          <InputField
            type="text"
            name="mailId"
            placeholder="메일 아이디"
            customStyles={{ width: '90px', height: '30px' }}
            disabled={isEmailVerified}
            value={emailId}
            onChange={handleEmailIdChange}
          />
          <span className={styles.at}>@</span>

          {!isCustomDomain ? (
            <Select
              options={emailOptions}
              placeholder="선택"
              customStyles={{
                width: '100px',
                height: '30px',
                color: '#7a7777',
              }}
              value={emailDomain}
              disabled={isEmailVerified}
              showValue={true}
              onChange={handleEmailDomainChange}
            ></Select>
          ) : (
            <InputField
              type="text"
              name="customDomain"
              placeholder="직접 입력"
              value={emailDomain}
              disabled={isEmailVerified}
              customStyles={{ width: '100px' }}
              onChange={handleCustomDomainChange}
            />
          )}
        </div>

        <div className={styles.authButtonContainer}>
          <Button onClick={onVerifyEmail} disabled={isEmailVerified}>
            인증
          </Button>
        </div>
      </div>

      {isEmailSent && (
        <div className={styles.emailVerifyContainer}>
          <InputField
            type="text"
            name="verifyNumber"
            placeholder="인증코드 입력"
            disabled={isEmailVerified || isExpired}
            customStyles={{ width: '213px', height: '30px' }}
            value={inputVerifyCode}
            onChange={handleVerifyCodeChange}
          />

          <div className={styles.authButtonContainer}>
            <Button
              onClick={onVerifyCode}
              disabled={isEmailVerified || isExpired}
            >
              확인
            </Button>
          </div>
        </div>
      )}
    </>
  );
};

export default EmailInputField;
