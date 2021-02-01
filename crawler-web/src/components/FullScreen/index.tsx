import React, { useState, useEffect, Component } from 'react';
import { CloseCircleOutlined } from '@ant-design/icons';
interface FullScreenProps{
  children:any
  visible:boolean
  onClose?:Function
  onClick?:any
  style?: React.CSSProperties
}
interface FullScreenState{
  
}
export default class FullScreen extends Component<
FullScreenProps,
FullScreenState
>{
  state={
  }
  render() {
    let {
      children,visible,onClose,style,onClick
    } = this.props;
  return (
    <div style={{
      display:visible?'block':'none',
      position: 'fixed',
      top: 0,
      left: 0,
      bottom: 0,
      width: '100%',
      overflowY: "scroll",
      zIndex: '999',
      backgroundColor:'rgba(0,0,0,0.5)',
      ...style
    }}
    onClick={onClick}
    >{children}
      <div style={{
        position: 'fixed',
        top: '30%',
        left: '2%'
      }}><CloseCircleOutlined style={{fontSize:'30px'}} onClick={() => {
        onClose()
      }} /></div>
    </div>
  )}
};
