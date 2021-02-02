import React,{useState,useEffect} from 'react';
import { Button, Image, message  } from 'antd';

import {CopyToClipboard} from 'react-copy-to-clipboard';
import request from 'umi-request';

interface IViewResultDetail{
  node:Node
}

const ViewResultDetail:React.FC<IViewResultDetail> =  (props)=>{
  const {node} = props
  const [ifExistInDB, setIfExistInDB] = useState(false)


  useEffect(() => {
    request.get('/carwelerJava/node/ifExistInDB/'+node.code).then((result)=>{
      setIfExistInDB(result)
    });
  }, [])

  const saveToDB = ()=>{
    request.post('/carwelerJava/node/save', {
      data: node,
    });
  }

  return <div style={{
    backgroundColor:'#fff',
    marginTop:20
  }}>
  <div>{node.code} 
    <Button onClick={saveToDB} disabled={ifExistInDB}>保存</Button>
  </div>
    <div>{node.title}</div>
    <div>{node.article}</div>
    <div>
      <Image
      width="50%"
        src={node.coverImg}
      />
      <Image
      width="50%"
        src={node.detailImg}
      />
    </div>
    <div>{node.magnet}
      <CopyToClipboard text={node.magnet}
      onCopy={()=>message.success('复制成功')}>
            <Button>复制</Button>
          </CopyToClipboard>
        </div>
  </div>
}
export default ViewResultDetail