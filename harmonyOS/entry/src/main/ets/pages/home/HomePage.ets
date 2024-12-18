import { apiService } from "../../api/Client"
import { ArticleEntity, BaseRespEntity } from "../../data/ArticleEntity"
import { ArticleDataSource } from "./ArticleDataSource"
import { BusinessError } from "@kit.BasicServicesKit"

@Component
struct HomePage {
  @State isRefreshing: boolean = false
  @State articleDataSource: ArticleDataSource = new ArticleDataSource()
  @State isListReachEnd: boolean = false
  @State page: number = 0

  async fetchData() {
    apiService.get<BaseRespEntity<ArticleEntity>>(`article/list/${this.page}/json`,)
      .then((result) => {
        if (this.page == 0) {
          this.articleDataSource.setupData(result.datas)
        } else {
          this.articleDataSource.addDataList(result.datas)
        }
        this.page++
      })
      .catch()
  }

  aboutToAppear(): void {
    console.debug("Article", "on AboutTo Appear")
    this.fetchData()
  }

  onDidBuild(): void {
    console.debug("Article", "onDidBuild")
  }

  aboutToDisappear(): void {
    console.debug("Article", "aboutToDisappear")
  }

  build() {
    NavDestination() {
      Refresh({ refreshing: this.isRefreshing, }) {
        Stack({ alignContent: Alignment.Top }) {
          List() {
            LazyForEach(this.articleDataSource, (item: ArticleEntity) => {
              ListItem() {
                ArticleItem({ data: item })
              }
            }, (item: ArticleEntity, index: number) => {
              return index + "_" + JSON.stringify(item)
            })
          }
          .height('100%')
          .width('100%')
          .margin({ top: 0 })
          .onReachEnd(() => {
            this.isListReachEnd = true
          })
          .parallelGesture(
            PanGesture({ direction: PanDirection.Up, distance: 80 })
              .onActionStart(() => {
                if (this.isListReachEnd) {
                  this.fetchData();
                  this.isListReachEnd = false;
                }
              }))
        }
      }
      .onStateChange((refreshStatus: RefreshStatus) => {
        if (refreshStatus == RefreshStatus.Refresh) {
          // this.refreshData()
        }
      })
      .onRefreshing(() => {
        this.refreshData()
      })
    }

  }

  loadMoreData() {
    if (this.isListReachEnd) {
      return
    }
    this.isListReachEnd = true

    this.isListReachEnd = false
  }

  refreshData() {
    this.isRefreshing = true
    this.fetchData()
    this.isRefreshing = false
  }
}

@Component
struct ArticleItem {
  @Prop data: ArticleEntity
  // 子页面中获取全局的NavPathStack
  pathStack: NavPathStack = AppStorage.get("PathStack") as NavPathStack

  build() {
    Column() {
      Text(this.data.title)
        .fontSize(16)
        .padding(16)
        .fontWeight(FontWeight.Normal)
        .fontColor("#ff16161a")
      // 你可以在这里添加其他的页面内容，如列表等
    }
    .onClick((event) => {
      console.debug("Article", `click item ${this.data.link}`)
      this.pathStack.pushDestination({
        name: "webPage",
        param: this.data.link
      })
        .catch((error: BusinessError) => {
          console.error(`Push destination failed, error code = ${error.code}, error.message = ${error.message}.`);
        }).then(() => {
        console.info('Push destination succeed.');
      });

    })
    .width('100%')
    .alignItems(HorizontalAlign.Start)
  }
}

export default HomePage;