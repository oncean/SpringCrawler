import React, { useState, useEffect } from 'react';
import { Modal, Card, List, Image, Button, Pagination, message } from 'antd';
import ViewResultDetail from './ViewResultDetail';
import { CopyToClipboard } from 'react-copy-to-clipboard';
import request from 'umi-request';
import { useHistory, useLocation } from 'umi';


export default () => {
  const [modelShow, setModelShow] = useState(false);
  const [currentNode, setCurrentNode] = useState<Node>();
  const [nodes, setnodes] = useState<Node[]>([]);
  const [total, setTotal] = useState(0);
  const location = useLocation();
  const taskId = location.query.taskId

  const onPageChange = async (pageNo: number, pageSize: number | undefined) => {
    setnodes([]);
    const data = await request.post('/carwelerJava/task/getByPage', {
      data: {
        taskId: taskId,
        pageNo,
        pageSize,
      },
    });
    setnodes(data.result || []);
    setTotal(data.total || 0);
  };
  useEffect(() => {
    onPageChange(1, 30);
  }, []);
  return (
    <div
      style={{
        backgroundColor: '#fff',
        marginTop: 20,
        width:'100%'
      }}
    >
      <List
        key="code"
        grid={{ gutter: 16, column: 8 }}
        dataSource={nodes}
        renderItem={(item: Node) => (
          <List.Item>
            <Card title={item.code} cover={<Image width="100%" src={item.coverImg} />}>
              <div>{item.title}</div>
              <div>{item.createTime}</div>
              <div>
                <Image width={50} src={item.detailImg} />
                <Button
                  onClick={() => {
                    setCurrentNode(item);
                    setModelShow(true);
                  }}
                >
                  查看详情
                </Button>
                <Button onClick={() => window.open(item.url)}>原链接</Button>
                <CopyToClipboard
                  text={item.magnet}
                  onCopy={() => message.success('复制成功')}
                >
                  <Button>复制磁力</Button>
                </CopyToClipboard>
              </div>
            </Card>
          </List.Item>
        )}
      />
      {total > 0 && (
        <Pagination
          style={{
            textAlign: 'right',
          }}
          defaultCurrent={1}
          defaultPageSize={30}
          showSizeChanger
          showQuickJumper
          pageSizeOptions={['30', '50', '100']}
          onChange={onPageChange}
          showTotal={(total, range) =>
            `${range[0]}-${range[1]} of ${total} items`
          }
          total={total}
        />
      )}

      {modelShow && currentNode && (
        <Modal
          width="100%"
          title="Basic Modal"
          visible
          onOk={() => setModelShow(false)}
          onCancel={() => setModelShow(false)}
        >
          <ViewResultDetail node={currentNode} />
        </Modal>
      )}
    </div>
  );
};
