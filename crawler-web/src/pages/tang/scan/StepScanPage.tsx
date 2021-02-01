import React from 'react';
import { Space, Spin } from 'antd';
import { CheckCircleOutlined, CloseCircleOutlined, LoadingOutlined } from '@ant-design/icons';
import Step from './Step';

export default (props:any) => {
  const {pages=[], status} = props
  let loading =false
  if (status === 'START_1'){
    loading = true
  }
  return (
    <div>
      <Step
      index="1"
      name="扫描列表"
      loading={loading}>
        {pages && 
          pages.map((page:any)=>(
            <div>
            <Space>
                    <div>{page.url}</div>
                    {
                      page.state === 'SUCCESS'&&
                      <Spin indicator={<CheckCircleOutlined />}/>
                    }
                    {
                      page.state === 'LOADING'&&
                      <Spin indicator={<LoadingOutlined spin/>}/>
                    }
                    {
                      page.state === 'ERROR'&&
                      <Spin indicator={<CloseCircleOutlined style={{color:'red'}} />}/>
                    }
                  </Space>
            </div>
          ))
      }
        </Step>
    </div>
  )
}