import React from 'react';
import Step from './Step';

export default (props:any) => {
  const {nodes=[], pages=[]} = props
  let successNodeNum = 0
  let errorNodeNum = 0
  nodes.forEach((node:any) => {
    if (node.state === 'SUCCESS'){
      successNodeNum+=1
    }
    if (node.state === 'ERROR'){
      errorNodeNum+=1
    }
  });
  let successPageNum = 0
  let errorPageNum = 0
  pages.forEach((page:any) => {
    if (page.state === 'SUCCESS'){
      successPageNum+=1
    }
    if (page.state === 'ERROR'){
      errorPageNum+=1
    }
  });

  return (
    <div>
    <Step
    name="扫描节点"
    loading={false}>
      
    <div>
      {`扫描到了${pages.length},加载${successPageNum},失败${errorPageNum}`}
    </div>
      <div>
        {`扫描到了${nodes.length},加载${successNodeNum},失败${errorNodeNum}`}
      </div>
      </Step>
    </div>
  )
}