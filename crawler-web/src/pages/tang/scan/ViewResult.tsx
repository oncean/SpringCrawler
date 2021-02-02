import React, { useState, useEffect } from 'react';
import { Modal, Card, List, Image, Button, Pagination } from 'antd';
import ViewResultDetail from './ViewResultDetail';
import request from 'umi-request';

interface props {
  taskId:string
}

export default (props: props) => {
  const [modelShow, setModelShow] = useState(false);
  const [currentNode, setCurrentNode] = useState({});
  const [nodes, setnodes] = useState<Node[]>([]);
  const [total, setTotal] = useState(0)

  const onPageChange = async (pageNo:number, pageSize:number| undefined)=>{
    const data = await request.post('/carwelerJava/task/getByPage', {
      data: {
        taskId:props.taskId,
        pageNo,pageSize
      },
    });
    setnodes(data.content||[])
    setTotal(data.totalElements||0)
  }
  useEffect(() => {
    onPageChange(1,30)
  }, [])  
  return (
    <div
      style={{
        backgroundColor: '#fff',
        marginTop: 20,
      }}
    >
      <List
        key="code"
        grid={{ gutter: 16, column: 4 }}
        dataSource={nodes}
        renderItem={(item: Node) => (
          <List.Item>
            <Card title={item.code} cover={<Image src={item.coverImg} />}>
              <div>{item.title}</div>
              <div>
                <Button
                  onClick={() => {
                    setCurrentNode(item);
                    setModelShow(true);
                  }}
                >
                  查看详情
                </Button>
                <Button onClick={() => window.open(item.url)}>原链接</Button>
              </div>
            </Card>
          </List.Item>
        )}
      />
      <Pagination 
      style={{
        textAlign:'right'
      }}
      defaultCurrent={1} 
      defaultPageSize={30}
      showSizeChanger
      showQuickJumper
      pageSizeOptions={['30','50','100']}
      onChange={onPageChange} 
      showTotal={(total, range) => `${range[0]}-${range[1]} of ${total} items`}
      total={total} />

      {modelShow && (
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
