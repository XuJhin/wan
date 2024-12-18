import { ArticleEntity } from "../../data/ArticleEntity"
import { BaseDataSource } from "../../data/base/BaseDataSource"

export class ArticleDataSource extends BaseDataSource<ArticleEntity> {
  public setupData(list: ArticleEntity[]) {
    this.clearData()
    this.newDataList(list)
  }
}