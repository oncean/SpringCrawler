import { Button, Card, List, Modal, Switch, message, Row, Tabs, Col } from 'antd';
import React, { Component } from 'react';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import { connect } from 'dva';
import _ from 'lodash';
import FullScreen from '@/components/FullScreen';
import WangPic from '@/components/WangPic/index'
import { UndoOutlined } from '@ant-design/icons';
import { reloadSmallFile } from '@/services/pic';
import Search from 'antd/lib/input/Search';
import request from '@/utils/request';
import { getByPage, dislike, like, fromCloud, cleanUseless, reloadNoPics, deleteBendi, openVideo } from './service';
import { PicCollection } from './data';

import { StateType } from './model';

const { TabPane } = Tabs;

let pageSize = 30
class ListCardList extends Component {
  state = {
    visible: false,
    current: {},
    phone: false,
    fullScreen: false,
    fullScreenDetail: false,
    fullScreenDetailSrc: {},
    data: [],
    type: 'all',
    loadingtable: false,
    hasMore: true,
    pageNum: 1,
    searchValue: ''
  };

  scroll = () => {
    const { loadingtable, hasMore } = this.state
    const scrollHeight = document.body.scrollHeight || document.documentElement.scrollHeight
    const scrollTop = document.body.scrollTop || document.documentElement.scrollTop
    if (scrollTop > scrollHeight - 1000) {
      console.info('滚动到底部')
      if (hasMore && !loadingtable) {
        this.handleInfiniteOnLoad()
      }
    }
  }

  componentDidMount() {
    window.addEventListener('scroll', this.scroll)
    switch (navigator.platform.toString()) {
      case 'iPhone':
        this.setState({ phone: true })
        pageSize = 10
        break
      default:
        break;
    }
    this.setState({searchValue:this.props.location.query.searchValue}, this.queryList)
    
  }

  componentWillUnmount() {
    window.removeEventListener('scroll', this.scroll)
  }

  queryList() {
    const { type, searchValue } = this.state
    this.setState({ data: [], loadingtable: true, hasMore:true }, async () => {
      const { data } = await getByPage({ num: 1, size: pageSize }, searchValue, type)
      this.setState({ data:data.data || [], loadingtable: false, total:data.total|| 0 })
    })
  }


  handleInfiniteOnLoad = () => {
    let { data, pageNum, searchValue, type } = this.state;
    pageNum++
    this.setState({
      loadingtable: true,
      pageNum
    });
    getByPage({ num: pageNum, size: pageSize }, searchValue, type).then(res => {
      const result = res.data.data || []
      if (result.length < pageSize) {
        this.setState({
          hasMore: false,
          loadingtable: false,
        });
        return;
      }
      data = data.concat(result);
      this.setState({
        data,
        loadingtable: false,
      });
    });
  };




  render() {
    const {
      data
    } = this.state;
    const { visible, current, phone, fullScreen, fullScreenDetail, fullScreenDetailSrc, hasMore, loadingtable, total, searchValue } = this.state
    const computer = !phone
    console.info(navigator.platform)
    const elements: any = [];
    if (fullScreen) {
      const a: PicCollection = current
      // 定义一个数组，将数据存入数组
      if (a && a.pics && a.pics.length > 0) {
        a.pics.forEach(pic => {
          elements.push(<WangPic pic={pic} style={{ width: '100%' }}
            onClick={() => this.setState({ fullScreenDetail: true, fullScreenDetailSrc: pic })} />)
        });
      }
    }
    const getNginxUrl = item =>{
      if (item.videoUrl && item.videoUrl.length > 0){
        let url = item.videoUrl[0]
        url = url.replace(/\\/g, '/')
        url = url.replace(/:/g, '')
        return `http://${  window.location.host.substring(0, window.location.host.indexOf(':'))  }/${url}`
      }
      return null
    }
    return (
      <PageHeaderWrapper>
        <Switch checkedChildren="电脑" unCheckedChildren="手机" checked={computer} onChange={(checked) => {
          console.log(`switch to ${checked}`);
          this.setState({
            phone: !checked
          })
        }} />
        <Card>
          <Row gutter={16}>
            <Col>
              <Button type="primary" icon={<UndoOutlined />} onClick={async () => {
                message.loading({ content: '扫描中..', key: 'carwelerZHainan', duration: 0 });
                await request('/carweler/bendi/scan')
                message.success({ content: '扫描成功!', key: 'carwelerZHainan', duration: 2 })
                this.queryList()
              }}>扫描</Button>
            </Col>
            <Col>
              <Button type="primary" icon={<UndoOutlined />} onClick={() => {
                this.queryList()
              }}>刷新</Button>
            </Col>
            <Col>
              <Button type="primary" icon={<UndoOutlined />} onClick={async () => {
                message.loading({ content: '抓取中..', key: 'carwelerZHainan', duration: 0 });
                await fromCloud()
                message.success({ content: '抓取成功!', key: 'carwelerZHainan', duration: 2 })
                this.queryList()
              }}>
                抓取云端</Button>
            </Col>
            <Col>
              <Button type="primary" icon={<UndoOutlined />} onClick={async () => {
                message.loading({ content: '清理中..', key: 'carwelerLaow', duration: 0 });
                await cleanUseless()
                message.success({ content: '清理成功!', key: 'carwelerLaow', duration: 2 })
                this.queryList()
              }}>
                清理无用图片（包括不喜欢的）</Button>
            </Col>
            <Col>
              <Button type="primary" icon={<UndoOutlined />} onClick={async () => {
                await reloadSmallFile(20 * 1024, 'zhainan')
                message.success('操作成功')
              }}>
                重新加载加载失败的图片</Button>
            </Col>
            <Col>
              <Button type="primary" icon={<UndoOutlined />} onClick={async () => {
                await reloadNoPics()
                message.success('操作成功')
              }}>
                重新加载没有子图片的</Button>
            </Col>
            <Col>
              <Search
                placeholder="input search text"
                enterButton="搜索"
                value={searchValue}
                onChange={(value)=>{
                  this.setState({searchValue:value.target.value})
                }}
                onSearch={value => this.setState({ searchValue: value }, this.queryList)}
              />
            </Col>
          </Row>
        </Card>
        <Card>
          <Tabs defaultActiveKey="1" onChange={(key) => {
            this.setState({
              type: key
            }, this.queryList)
          }}>
            <TabPane tab="主页" key="all">
            </TabPane>
            <TabPane tab="喜欢" key="like">
            </TabPane>
            <TabPane tab="不喜欢" key="dislike">
            </TabPane>
            <TabPane tab="未下载" key="notDownload">
            </TabPane>
          </Tabs>
        <div>总共：{total}</div>
        </Card>
        {data.length > 0 &&
          <List
            grid={{ column: phone ? 2 : 5 }}
            dataSource={data}
            renderItem={item => (
              <List.Item>
                <Card hoverable cover={<img style={{ width: '100%' }} src={`/carweler/bendi/getPic/${item.code}`} onClick={
                  (e) => {
                    e.stopPropagation()
                    this.setState({
                      visible: true,
                      current: {}
                    }, ()=>this.setState({current:item}))
                  }
                } />}
                  actions={[
                    <span onClick={() =>
                      this.setState({
                        visible: true,
                        current: {}
                      }, ()=>this.setState({current:item}))
                    }>浏览</span>,
                    <span onClick={() => {
                      this.setState({
                        fullScreen: true,
                        current: item
                      })
                    }}>全屏浏览</span>,
                  ]}>
                  <Card.Meta description={
                    <div>
                      <div style={{ height: '75px' }}>{item.zh || item.name}</div>
                      <Row gutter={10}>
                        <Col>
                          {item.printDate}
                        </Col>
                        <Col>
                          {item.length}
                        </Col>
                        <Col>
                        {(!item.videoUrl || item.videoUrl.length < 1) ?
                        <Button disabled>未找到播放位置</Button>
                        :
                        <span>
                          <Button onClick={()=>{
                            window.open(getNginxUrl(item));
                          }}>播放nginx</Button>
                          <Button onClick={()=>{
                            openVideo(item.videoUrl)
                          }}>本地播放</Button>
                        </span>
                        }
                        
                          <Button onClick={async () => {
                            message.loading({ content: '收藏中..', key: 'carwelerZHainan', duration: 0 });
                            await like(item.code)
                            data.forEach(element => {
                              if (element.code === item.code) {
                                element.like = true
                              }
                            });
                            this.setState({
                              data
                            })
                            message.success({ content: '收藏成功!', key: 'carwelerZHainan', duration: 2 })
                          }}>{item.like ? '已收藏' : '收藏'}</Button>
                        </Col>
                        <Col>
                          <Button onClick={async () => {
                            _.remove(data, (o) => {
                              return o.code === item.code
                            })
                            await dislike(item.code)
                            this.setState({
                              data
                            })
                          }}>不喜欢</Button>
                        </Col>
                        <Col>
                          <Button onClick={ () => {
                            Modal.confirm({
                              title:'确定删除',
                              onOk:async ()=>{
                                _.remove(data, (o) => {
                                  return o.code === item.code
                                })
                                await deleteBendi(item.code)
                                message.success('删除成功')
                                this.setState({
                                  data
                                })
                              }
                            })
                          }}>删除</Button>
                        </Col>
                        <Col>
                          <a href={`https://www.javbus.com/${item.replaceCode||item.code}`} target="_blank">原链接</a>
                        </Col>
                        <Col>
                          <a href={`${getNginxUrl(item)  }?type=download`} target="_blank">下载</a>
                        </Col>
                        <Col>
                          <a href={`/public/index.html#/public/bendi?searchValue=${item.article}`} target="_blank">{item.article}</a>
                        </Col>
                      </Row>
                      <Row>
                        {item.videoUrl}
                      </Row>
                    </div>} />
                </Card>
              </List.Item>
            )
            }
          >
          </List>
        }
        {hasMore || loadingtable ?
          <Row justify="center"><Button size="large" loading={loadingtable} onClick={() => {
            this.handleInfiniteOnLoad()
          }}>加载更多</Button></Row>
          :
          <Row justify="center">已经没有更多数据了</Row>
        }
        {
          <Modal
            visible={visible}
            title={null}
            closable={false}
            footer={null}
            onCancel={() => this.setState({ visible: false })}
            width="80%"
          >

            {current.examples && current.examples.length > 0 && <List
              grid={{ column: phone ? 2 : 4 }}
              dataSource={current.examples}
              pagination={{
                onChange: page => {
                  console.log(page);
                },
                showTotal:total => `Total ${total} items`,
                pageSize: 20,
              }}
              renderItem={item => (
                <List.Item>
                  <WangPic style={{ width: '100%' }} pic={item}  onClick={()=>{
                  this.setState({
                    fullScreenDetail:true,
                    fullScreenDetailSrc:item
                  })
                }}/>
                </List.Item>
              )}
            />}
            <Modal visible={fullScreenDetail} width="80%" onCancel={()=>{
              this.setState({fullScreenDetail:false})
            }}>
              <WangPic style={{width:'100%', maxHeight:'100%'}} pic={fullScreenDetailSrc}/>
            </Modal>
          </Modal>
        }
        <FullScreen visible={fullScreen} onClose={() => {
          this.setState({ fullScreen: false })
        }}>
          {elements}
        </FullScreen>

      </PageHeaderWrapper >
    );
  }
}

export default connect(
  ({
    listCardList2,
    loading,
  }: {
    listCardList2: StateType;
    loading: {
      models: { [key: string]: boolean };
    };
  }) => ({
    listCardList2,
    loading: loading.models.listCardList,
  }),
)(ListCardList);
