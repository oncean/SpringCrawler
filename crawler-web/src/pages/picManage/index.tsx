import { PageHeaderWrapper } from '@ant-design/pro-layout';
import React, { Component } from 'react';
import { Spin, Table, Row, Col, Card, Button, message, Select } from 'antd';
import { getByPage, downLoadAllPics, updateUnload } from './service';
import { Pic } from '@/services/pic';
import WangPic from '@/components/WangPic/index'



class PicManage extends Component {
  state = {
    page: {
      num: 1,
      size: 10,
    },
    list: [],
    total: 0,
    loadingAll: false,
    selectedRowKeys: [],
    selectType: 'all'
  }
  componentDidMount() {
    this.query()
  }
  query = async () => {
    const { page, selectType } = this.state
    const { data } = await getByPage(page, { status: selectType })
    this.setState({
      list: data.data || [],
      total: data.total,
      selectedRowKeys: []
    })
  }

  onSelectChange = (selectedRowKeys: any) => {
    console.log('selectedRowKeys changed: ', selectedRowKeys);
    this.setState({ selectedRowKeys });
  };
  render() {
    const { page, list, total, loadingAll, selectedRowKeys } = this.state

    const columns = [
      {
        title: 'id',
        dataIndex: 'id'
      },
      {
        title: 'Name',
        dataIndex: 'name',
      },
      {
        title: 'pic',
        render: (text, record) => {
          return <WangPic pic={record} style={{ maxWidth: '100px', maxHeight: '70px' }}></WangPic>
        }
      },
      {
        title: 'url',
        dataIndex: 'url',
      },
      {
        title: '是否等待加载  ',
        dataIndex: 'waitForLoad',
        render: (text: boolean) => text ? '未加载' : '已加载'
      },
      {
        title: 'dirName',
        dataIndex: 'dirName'
      }
    ];

    const rowSelection = {
      selectedRowKeys,
      onChange: this.onSelectChange,
    };
    return (
      <PageHeaderWrapper>
        <Row gutter={16}>
          <Col span={8}>
            <Card title="图片总数" bordered={false}>
              {total}
            </Card>
          </Col>
          <Col span={8}>
            <Card title="未加载的图片数" bordered={false}>
              <Spin spinning={loadingAll}>
                <Button onClick={async () => {
                  this.setState({
                    loadingAll: true
                  })
                  await downLoadAllPics()
                  this.setState({
                    loadingAll: false
                  })
                  message.success('成功')
                  this.query()
                }}>加载所有图片</Button>
              </Spin>
            </Card>
          </Col>
          <Col span={8}>
            <Card title="Card title" bordered={false}>
              Card content
        </Card>
          </Col>
        </Row>
        <Card>
          <Select defaultValue="all" style={{ width: 120 }} onChange={(value) => {
            this.setState({ selectType: value }, this.query)
          }}>
            <Select.Option value="all">所有</Select.Option>
            <Select.Option value="wait">未加载</Select.Option>
            <Select.Option value="loaded">已加载</Select.Option>
          </Select>
          <Button onClick={async () => {
            message.loading({ content: '操作中..', key: 'carwelerZHainan', duration: 0 });
            await updateUnload(selectedRowKeys)
            message.success({ content: '操作成功!', key: 'carwelerZHainan', duration: 2 })
            this.query()
          }}>更新为未加载</Button>
        </Card>
        <Table<Pic> rowSelection={rowSelection} columns={columns} dataSource={list} pagination={{
          total: total,
          showSizeChanger: true,
          onChange: (current) => {
            page.num = current
            this.setState({
              page
            })
            this.query()
          },
          showTotal:total => `Total ${total} items`,
          current: page.num,
          pageSize: page.size,
          onShowSizeChange: (_current: any, pageSize: number) => {
            page.size = pageSize
            this.setState({
              page
            })
            this.query()
          }
        }}
          rowKey={(record) => record.name} />
      </PageHeaderWrapper>
    )
  }
}
export default PicManage;