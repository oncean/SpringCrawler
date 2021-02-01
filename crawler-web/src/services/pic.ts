import request from '@/utils/request';

export async function getPicByName(picName:string){
  const data =  await request('/carweler/pic/getPicByName',{
    params:{
      picName
    }
  })
  return data 
}

export async function getOrign(pic:Pic){
  return request.post('/carweler/pic/getOrign',{
    data:{
      pic
    }
  })
}
export async function reloadSmallFile(minSize: number, dirName: string){
  return request.post('/carweler/pic/reloadSmallFile',{
    data:{
      minSize,dirName
    }
  })
}
export interface Pic{
  id?: string;
  name?: string;
  url?: string;
  dirName?: string;
  loaded?: boolean;
  waitForLoad?: boolean;
}