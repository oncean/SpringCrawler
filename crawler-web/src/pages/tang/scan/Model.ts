enum ScanItemState{
  NEW,LOADING,SUCCESS,ERROR
}
interface ScanItem{
  url:string,
  state:ScanItemState,
  errMsg:string
}

interface Node extends ScanItem{
  createTime?:string,
  title?:string,
  article?:string,
  coverImg?:string,
  detailImg?:string,
  magnet:string,
  code?:string
}

interface MainPage extends ScanItem{
  nodeNum:number
}

interface Result {
  mainPageList:Array<MainPage>,
  nodes:Array<Node>,
  status:string,
  current:number
}