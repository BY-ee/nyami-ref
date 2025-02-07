import React from 'react';
import styles from './Container.module.css';

const Container = ({ children }) => {
  return (
    <div className={styles.container}>
      <div className={styles.wrapper}>{children}</div>
    </div>
  );
};

export default Container;
