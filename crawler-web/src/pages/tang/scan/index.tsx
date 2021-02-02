/* eslint-disable no-debugger */
import React, {  } from 'react';
import { Input } from 'antd';
import { history } from 'umi';
import request from 'umi-request';
interface IScanViewProps{
}

const { Search } = Input;
const ScanView:React.FC<IScanViewProps> = () => {
  
  const startTask = async (pageNums: String[])=>{
    const data = await request.post('/carwelerJava/task/create', {
      data: {
        pages: pageNums,
      },
    });
    window.open(history.createHref(history.location))
    history.push({
      pathname:'/tang/scanTask',
      query:{
        taskId:data
      }
    });
  }


  const onSearch = (value:string) => {
    startTask(value.split(' '))
  };
  return <div>
    <div style={{
    backgroundColor:'#fff',
    minHeight:300,
    padding:50,
  }}>
    <Search
      placeholder="input page total"
      enterButton="start"
      size="large"
      style={{
        width:300,
        margin:'auto',
        display:'block'
      }}
      onSearch={onSearch}
    />
    </div>
  </div>;
};
export default ScanView