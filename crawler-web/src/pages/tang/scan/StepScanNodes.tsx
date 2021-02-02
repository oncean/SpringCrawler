import React from 'react';
import { Space, Spin, Progress } from 'antd';
import { CloseCircleOutlined, LoadingOutlined,ClockCircleOutlined } from '@ant-design/icons';
import Step from './Step';

export default (props: any) => {
  const { nodes = [], status } = props;
  let loading = false;
  if (status === 'START_2') {
    loading = true;
  }
  let index = 0;
  nodes.forEach((node: any) => {
    if (node.state === 'SUCCESS') {
      index += 1;
    }
  });

  return (
    <div>
      <Step name="扫描节点" loading={loading}>
        <div>
          <div>
            <Progress percent={index/nodes.length} size="small" format={()=>`加载${index}/${nodes.length}`} />
          </div>

          <div
            style={{
              maxHeight: 200,
              overflow: 'auto',
            }}
          >
            {nodes &&
              nodes
                .filter((page: any) => page.state !== 'SUCCESS')
                .map((node: any) => (
                  <div>
                    <Space>
                      <div>{node.url}</div>
                      {node.state === 'NEW' && <Spin indicator={<ClockCircleOutlined />} />}
                      {node.state === 'LOADING' && <Spin indicator={<LoadingOutlined spin />} />}
                      {node.state === 'ERROR' && (
                        <Spin indicator={<CloseCircleOutlined color="red" />} />
                      )}
                    </Space>
                  </div>
                ))}
          </div>
        </div>
      </Step>
    </div>
  );
};
