import React, { useState } from 'react';
import InputField from '../inputField/InputField';
import styles from './EmailInputField.module.css';
import Button from '../button/Button';
import Select from '../select/Select';
const EmailInputField = ({ onEmailChange }) => {
  const [emailId, setEmailId] = useState('');
  const [emailDomain, setEmailDomain] = useState('');
  const [isCustomDomain, setIsCustomDomain] = useState(false); // 직접 입력 활성화 여부

  const handleEmailIdChange = (e) => {
    setEmailId(e.target.value);
    onEmailChange(`${e.target.value}@${emailDomain}`);
  };
  const handleEmailDomainChange = (value) => {
    if (value === '직접 입력') {
      setIsCustomDomain(true);
      setEmailDomain(''); // 직접 입력 활성화
    } else {
      setIsCustomDomain(false);
      setEmailDomain(value); // 선택한 도메인 설정
      onEmailChange(`${emailId}@${value}`);
    }
  };
  const handleCustomDomainChange = (e) => {
    setEmailDomain(e.target.value);
    onEmailChange(`${emailId}@${e.target.value}`);
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
    <div className={styles.emailContainer}>
      <div className={styles.emailInputWrapper}>
        <InputField
          type="text"
          name="mailId"
          placeholder="메일 아이디"
          customStyles={{ width: '90px', height: '30px' }}
          value={emailId}
          onChange={handleEmailIdChange}
        />
        <span className={styles.at}>@</span>

        {!isCustomDomain ? (
          <Select
            options={emailOptions}
            placeholder="선택"
            customStyles={{ width: '100px', height: '30px', color: '#7a7777' }}
            value={emailDomain}
            showValue={true}
            onChange={handleEmailDomainChange}
          ></Select>
        ) : (
          <InputField
            type="text"
            name="customDomain"
            placeholder="직접 입력"
            value={emailDomain}
            customStyles={{ width: '100px' }}
            onChange={handleCustomDomainChange}
          />
        )}
      </div>

      <div className={styles.authButtonContainer}>
        <Button type="submit">인증</Button>
      </div>
    </div>
  );
};

export default EmailInputField;
