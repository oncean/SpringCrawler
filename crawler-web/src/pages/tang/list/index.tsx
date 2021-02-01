import React, { useState, useEffect } from 'react';
import { List, Space, Pagination, Button, Affix, message } from 'antd';
import request from 'umi-request';
import Search from 'antd/lib/input/Search';
import DetailView from './DetailView';
import { history } from 'umi';

interface ITangList{
  location:{
    query:{
      page:number
    }
  }
}

interface IList{
  total?:number,
  data?:any[]
}

const TangList:React.FC<ITangList> = (props) => {
  const [loading, setLoading] = useState<boolean>(true);

  const [list, setlist] = useState<IList>({});
  const [searchValue, setSearchValue] = useState('');
  const { page = 1 } = props.location.query;
  const query = async () => {
    setlist({});
    setLoading(true);
    const result = await request.post('/carweler/bendi/querySehua', {
      data: {
        type: 'all',
        searchValue,
        page: { num: page, size: 30 },
      },
    });
    setlist(result.data);
    setLoading(false);
  };
  const scan = async () => {
    setlist({});
    setLoading(true);
    try {
      await request('/carweler/bendi/scanSehua');
      query();
    } catch (error) {
      message.error(error);
    }
    setLoading(false);
  };

  useEffect(() => {
    query();
  }, [page]);

  return (
    <div>
      <Affix>
        <Space>
          <Pagination
            defaultCurrent={page}
            current={page}
            total={list.total}
            onChange={current => {
              history.push(`/sehua?page=${current}`);
            }}
          />
          <Button
            onClick={() => {
              query();
            }}
          >
            刷新
          </Button>
          <Button
            onClick={() => {
              scan();
            }}
          >
            扫描
          </Button>

          <Search
                placeholder="input search text"
                enterButton="搜索"
                value={searchValue}
                onChange={(value)=>{
                  setSearchValue(value.target.value)
                }}
                onSearch={value => {
                  setSearchValue(value)
                  query()
                }}
              />
        </Space>
      </Affix>

      {list && (
        <List
          dataSource={list.data}
          loading={loading}
          gird={{column:2}}
          renderItem={item => (
            <div>
              <DetailView item={item} />
            </div>
          )}
        />
      )}
    </div>
  );
};
export default TangList;