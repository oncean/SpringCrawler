import React, { Component } from 'react';
import { getPicByName, getOrign, Pic } from '../../services/pic'; import { CopyFilled } from '@ant-design/icons';
import { message, Spin } from 'antd';
;
interface FullScreenProps {
  pic: Pic
  onClick?: any
  style?: React.CSSProperties
}
interface FullScreenState {
  loading: boolean
}
export default class FullScreen extends Component<
  FullScreenProps,
  FullScreenState
  >{
  state = {
    loading: false
  }
  refresh = async () => {

    let { pic
    } = this.props;
    message.loading({ content: `${pic.name}刷新中..`, key: `carwelerLao${pic.name}`, duration: 0 });
    this.setState({
      loading: true
    })
    await getOrign(pic)
    this.setState({
      loading: false
    })
    message.success({ content: `${pic.name}刷新成功!`, key: `carwelerLao${pic.name}`, duration: 2 })
  }
  render() {
    let {
      onClick, style, pic
    } = this.props;
    const { loading } = this.state
    const Img = pic && pic.name ?<img src={`/carweler/pic/getPicByName/${pic.name}`} onClick={onClick} style={{
      maxHeight: '100%', maxWidth: '100%',
      ...style
    }} />:<></>
    return (
      pic && pic.name ?
      <div style={{
        position: 'relative',
        backgroundColor: '#fff'
      }}>
        {loading ?
          <Spin spinning={loading}>
            {Img}
          </Spin>
          :
          Img
        }
        <div style={{
          position: 'absolute',
          bottom: '0px',
          left: '0px',
          width: '50px',
          height: '50px'
        }}
          onClick={this.refresh} />
        <CopyFilled style={{
          position: 'absolute',
          bottom: '0px',
          left: '0px',
          fontSize: '8px'
        }}
          onClick={this.refresh} />
      </div>
      :
      <div></div>
    )
  }
};
