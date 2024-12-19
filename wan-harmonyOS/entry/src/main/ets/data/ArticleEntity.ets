export interface ArticleEntity {
  title: string
  id: number
  link:string
}

export interface BaseResp<T>{
  errorCode:number
  errorMsg:string
  data:T
}
export interface BaseRespEntity<T>{
  datas:[T]
  curPage:number

}