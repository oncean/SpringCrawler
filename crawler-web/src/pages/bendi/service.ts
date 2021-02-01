import request from '@/utils/request';
import { Page } from '@/services/global';

export async function queryList() {
  return request('/carweler/bendi/getAll');
}

export async function getByPage(page:Page, searchValue:string, type?: 'like'|'dislike'|'all') {
  return request.post('/carweler/bendi/getByPage', {
    data:{
      page, type, searchValue
    }
  });
}

export async function dislike(href:string){
  return request.post('/carweler/bendi/dislike', {
    data:{
      href
    }
  })
}
export async function deleteBendi(code:string){
  return request(`/carweler/bendi/deleteVideo/${code}`)
}



export async function like(href:string){
  return request.post('/carweler/bendi/like', {
    data:{
      href
    }
  })
}
export async function openVideo(url:string){
  return request.post('/carweler/bendi/openVideo', {
    data:{
      url
    }
  })
}


export async function fromCloud(){
  return request('/carweler/bendi/fromCloud')
}
export async function cleanUseless(){
  return request('/carweler/bendi/cleanUseless')
}

export async function reloadNoPics(){
  return request('/carweler/bendi/reloadNoPics')
}
