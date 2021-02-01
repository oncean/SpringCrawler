import React, {useState} from 'react'
import { Modal, Card, List, Image, Button  } from 'antd'
import ViewResultDetail from './ViewResultDetail';

interface props{
  nodes:Node[]
}

export default (props:props)=>{
  const {nodes=[]} = props
  const [modelShow, setModelShow] = useState(false);
  const [currentNode, setCurrentNode] = useState({});

  return <div style={{
    backgroundColor:'#fff',
    marginTop:20
  }}>
    <List
    key="code"
    grid={{ gutter: 16, column: 4 }}
    dataSource={nodes}
    renderItem={(item:Node) => (
      <List.Item>
        <Card title={item.code}
        cover={
          <Image
            src={item.coverImg}
          />}
        >
        <div>{item.title}</div>
        <div>
          <Button onClick={()=>{
            setCurrentNode(item)
            setModelShow(true)
          }}>查看详情</Button>
          <Button onClick={()=>window.open(item.url)}>
            原链接
          </Button>
        </div>
        </Card>
      </List.Item>
    )}
  />
  
  {modelShow&&
  <Modal width="100%" title="Basic Modal" visible onOk={()=>setModelShow(false)} onCancel={()=>setModelShow(false)}>
        <ViewResultDetail node={currentNode}/>
      </Modal>}
  </div>
}