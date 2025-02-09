import React from 'react';
import styles from './BasicModal.module.css';
import Button from '../button/Button';

/**
 *
 * @param {Function} isOpen - 모달 열기 함수
 * @param {Function} onClose - 모달 닫기 함수
 * @param {string} width - 모달 컨테이너의 가로 길이
 * @param {string} height - 모달 컨테이너의 세로 길이
 * @param {any} children - 모달 내부의 내용
 */
const BasicModal = ({ isOpen, onClose, width, height, children }) => {
  if (!isOpen) return null;

  return (
    <div className={styles.modalContainer} style={{ width, height }}>
      <div className={styles.content}>{children}</div>

      <div className={styles.buttonContainer}>
        <Button onClick={onClose}>닫기</Button>
      </div>
    </div>
  );
};

export default BasicModal;
