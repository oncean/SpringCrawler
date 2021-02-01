import React from 'react';
import { Spin } from 'antd';
import { CheckCircleOutlined,LoadingOutlined } from '@ant-design/icons';

export default (props:any) => {
  const {name, loading=true} = props

  return (
    <div>
      <div style={{
        display:'flex',
        justifyContent:'space-between'
      }}>
        <div style={{
            fontSize:20,
            lineHeight:'20px'
          }}>
          <span>
          {loading?
          <Spin indicator={<LoadingOutlined spin />}/>
          :<CheckCircleOutlined style={{
            color:'#1890ff'
          }}/>}
          </span>
          <span style={{fontSize:16, marginLeft:16}}>{name}</span>
        </div>
        <div>
          <Spin spinning={loading}/>
        </div>
      </div>
      <div style={{
        marginLeft:40,
        padding:'20px 0'
      }}>
      {props.children}
      </div>
    </div>
  )
}