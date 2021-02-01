import request from '@/utils/request';
import { Page } from '@/services/global';

export async function getByPage(page:Page,condition:any) {
  return request.post('/carweler/pic/getByPage', {
    data:{
      page,
      condition
    }
  });
}
export async function downLoadAllPics(){
  return request('/carweler/pic/downLoadAllPics',)
}
export async function updateUnload(names){
  return request.post('/carweler/pic/updateUnload',{
    data:{
      names
    }
  })
}

export async function getOrign(pic){
  return request.post('/carweler/pic/getOrign',{
    data:{
      pic
    }
  })
}
export async function like(href){
  return request.post('/carweler/laow/like',{
    data:{
      href
    }
  })
}
export async function fromCloud(){
  return request('/carweler/laow/fromCloud')
}
export async function cleanUseless(){
  return request('/carweler/laow/cleanUseless')
}
