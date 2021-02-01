import React, { useState, useEffect } from 'react';
import { Skeleton, Modal } from 'antd';
import Fail from './fail.jpg';

export default props => {
  const { src, width = '100%' } = props;
  const [loading, setLoading] = useState(true);
  const [fail, setFail] = useState(false);
  const [visible, setVisible] = useState(false);
  const [detail, setDetail] = useState('');

  const loadImg = () => {
    setFail(false);
    const img = new Image();
    img.onload = () => {
      setLoading(false);
    };
    img.onerror = () => {
      setFail(true);
    };
    img.src = src;
  };

  useEffect(() => {
    loadImg();
  }, []);

  if (fail) {
    return (
      <div
        onClick={() => {
          loadImg();
        }}
      >
        <img alt="123" src={Fail} />
      </div>
    );
  }

  let page = <Skeleton.Image />;

  if (!loading) {
    page = (
      <img
        src={src}
        style={{
          maxHeight: 400,
          maxWidth: '100%',
        }}
        alt="sdsd"
        onClick={() => {
          setDetail(src);
          setVisible(true);
        }}
      />
    );
  }

  return (
    <div style={{ width, display: 'inline-block' }}>
      {page}
      <Modal
        title=""
        visible={visible}
        onCancel={() => {
          setVisible(false);
        }}
        width="100%"
        bodyStyle={{
          padding: 0,
        }}
      >
        <div
          onClick={() => {
            setVisible(false);
          }}
        >
          {detail && detail && (
            <img
              alt="123"
              src={detail}
              style={{
                maxHeight: 800,
                maxWidth: '100%',
              }}
            />
          )}
        </div>
      </Modal>
    </div>
  );
};
