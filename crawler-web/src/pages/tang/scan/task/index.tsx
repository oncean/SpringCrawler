/* eslint-disable no-debugger */
import React, { useState, useEffect } from 'react';
import { Button, Spin } from 'antd';
import websocket from 'websocket';
import request from 'umi-request';
import StepScanPage from './StepScanPage';
import StepScanNodes from './StepScanNodes';
import StepScanResult from './StepScanResult';
import { history } from 'umi';

interface IScanViewProps{
  location:{
    query:{
      taskId:string
    }
  }
}

const TaskPage:React.FC<IScanViewProps> = (props) => {
  const { taskId } = props.location.query;
  const [pages, setpages] = useState<MainPage[]>([]);
  const [status, setstatus] = useState(0);
  const [taskState, setTaskState] = useState('NEW');
  const [nodes, setnodes] = useState<Node[]>([]);
  
  const retry = ()=>{
    request.get(`/carwelerJava/task/retry/${taskId}`);
  }
  
  const buildSteps = (result:Result)=>{
    if(!result){
      return
    }
    setTaskState(result.status)
    if (result.status === 'START_1'){
      setpages(result.mainPageList||[])
      setstatus(1)
    } else if (result.status === 'START_2'){
      setnodes(result.nodes||[])
      setstatus(2)
    } else if (result.status === 'END'){
      setpages(result.mainPageList||[])
      setnodes(result.nodes||[])
      setstatus(3)
    }
  }
  useEffect(() => {
    if (!taskId){
      return
    }
    const client = new websocket.w3cwebsocket(`ws://localhost:8081/websocket/scan/${taskId}`)
    client.onmessage =( e:any) => {
      if (typeof e.data === 'string') {
        buildSteps(JSON.parse(e.data))
      }
    }
    return ()=>{
      client.close()
    }
  }, [taskId]);


  const steps = ()=>{
    let div
    if (status <= 0){
      div = <Spin/>
    } else if (status<=1){
      div = <StepScanPage pages={pages}/>
    } else if (status <=2) {
      div = <div>
        <StepScanPage pages={pages}/>
        <StepScanNodes nodes={nodes}/>
      </div>
    } else {
      div = <div>
        <StepScanPage pages={pages} status={taskState}/>
        <StepScanNodes nodes={nodes} status={taskState}/>
        <StepScanResult  pages={pages} nodes={nodes}/>
        <div style={{textAlign:'center'}}>
              <Button onClick={retry}>重新扫描</Button>
              <Button onClick={()=>{
                history.push({
                  pathname:'/tang/scanResult',
                  query:{
                    taskId:taskId
                  }
                })
              }}>显示结果</Button>
        </div>
      </div>
    }
    return div
  }
  let stepView = steps()
  
  useEffect(() => {
    stepView = steps()
  }, [status]);

  return <div>
    <div style={{
    backgroundColor:'#fff',
    minHeight:300,
    padding:50,
  }}>
    {
      taskId &&(
        <div>
          <div style={{
            width:400,
            margin:'auto'
          }}>
            {stepView}
          </div>
        </div>
      )
    }
  </div>

  </div>;
};
export default TaskPage