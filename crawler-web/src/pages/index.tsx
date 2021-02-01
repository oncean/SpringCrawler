import styles from './index.less';
import React from 'react';
import {Button} from 'antd'
import request from 'umi-request';

export default function IndexPage() {
  return (
    <div>
      <h1 className={styles.title}>Page index</h1>

      <Button
        onClick={() => {
          request('/carweler/shutdown');
        }}
      >
        关机
      </Button>
    </div>
  );
}
