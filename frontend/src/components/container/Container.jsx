import React from 'react';
import styles from './Container.module.css';

const Container = ({ children, width, height }) => {
  return (
    <div className={styles.container}>
      <div className={styles.wrapper} style={{ width, height }}>
        {children}
      </div>
    </div>
  );
};

export default Container;
