export interface Page{
  num:number
  size:number
}

export interface PageResult<T>{
  data:T[]
  total:number
}