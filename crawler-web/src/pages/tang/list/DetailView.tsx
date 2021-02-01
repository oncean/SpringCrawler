import React, { useState } from 'react';
import { Card, Space, Button, Input, List } from 'antd';
import ImageBox from '@/components/ImageBox';
import ImagesView from '@/components/ImagesView';
import { Link } from 'umi';

export interface Pic{
  id: string;
  name: string;
  url: string;
  dirName: string;
  loaded: boolean;
  minSize: number;
  waitForLoad: boolean;
}

interface DetailItem{
  url:string,
  name:string,
  page:number,
  order:string,
  code:string,
  type:string,
  cover:string,
  examples:Pic[],
  torrent:string,
  date:string,
}

interface IDetailView{
  item:DetailItem
}

function getPicUrl(pic:DetailItem){
  return `/carweler/pic/getPicByName/${pic.name}`
}
const DetailView:React.FC<IDetailView> =  props => {
  const { url, name, page, order, code, type, cover, examples = [], torrent, date } = props.item;
const [detailFlag, setDetailFlag] = useState(false);
  const javImgs:string[] = [];
  examples.forEach((img) => {
    javImgs.push(getPicUrl(img));
  });

  const [opens, setOpens] = useState(false);
  return (
    <Card hoverable style={{ width: 800, maxWidth: '100%', marginBottom: 20 }} bordered extra={<a onClick={()=>{
      setDetailFlag(true)
    }}>More</a>}>
    <p>{name}</p>
  <p>{page} -{order}</p>
      {detailFlag && (
      <div>
          <p>{code}</p>
      <p>{type}</p>
      <p>{date}</p>
      <div>
        {examples &&
        <List
        grid={{column:4}}
            bordered
            dataSource={examples}
            renderItem={item => (<ImageBox src={getPicUrl(item)} width="100%" />)}
        />
        }
      </div>
      <p>
        <Input placeholder="Basic usage" value={torrent} disabled />
      </p>
      <Space>
        <Button
          onClick={() => {
            window.open(`https://avgle.com/search/videos?search_query=${code}&search_type=videos`);
          }}
        >
          avgle
        </Button>
        <Button>
          <Link to={`/javbus?code=${code}&zh=${name}&torrent=${torrent}`} target="_blank">
            javbus-bendi
          </Link>
        </Button>
        <Button
          onClick={() => {
            window.open(`https://www.javbus.com/${code}`);
          }}
        >
          javbus
        </Button>
        <Button
          onClick={() => {
            window.open(`https://sehuatang.org/${url}`);
          }}
        >
          原链接
        </Button>
        <Button
          onClick={() => {
            setOpens(true)
          }}
        >
          画廊
        </Button>
      </Space>
       </div>
      )}
      
      <div>
        {cover && <ImageBox src={getPicUrl(cover)} width="100%" />}
      </div>
      
      {javImgs && (
        <ImagesView
          images={javImgs}
          open={opens}
          onClose={() => {
            setOpens(false);
          }}
        />
      )}
    </Card>
  );
};
export default DetailView;