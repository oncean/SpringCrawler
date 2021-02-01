import React from 'react';
import { Button, Image, message  } from 'antd';

import {CopyToClipboard} from 'react-copy-to-clipboard';



export default (props:any)=>{
  const {node={}} = props

  return <div style={{
    backgroundColor:'#fff',
    marginTop:20
  }}>
  <div>{node.code}</div>
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