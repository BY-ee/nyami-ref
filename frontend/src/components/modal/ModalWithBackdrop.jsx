import React from 'react';
import BasicModal from './BasicModal';
import styles from './ModalWithBackdrop.module.css';

/**
 * 모달 외의 영역을 클릭할 수 없는 모달 컴포넌트
 * @param {Function} isOpen - 모달 열기 함수
 * @param {Function} onClose - 모달 닫기 함수
 * @param {string} width - 모달 컨테이너의 가로 길이
 * @param {string} height - 모달 컨테이너의 세로 길이
 * @param {any} children - 모달 내부의 내용
 */
const ModalWithBackdrop = ({ isOpen, onClose, width, height, children }) => {
  if (!isOpen) return null;

  const handleBackdropClick = (e) => {
    e.stopPropagation();
  };

  return (
    <div className={styles.backdrop} onClick={handleBackdropClick}>
      <BasicModal
        isOpen={isOpen}
        onClose={onClose}
        width={width}
        height={height}
      >
        {children}
      </BasicModal>
    </div>
  );
};

export default ModalWithBackdrop;
