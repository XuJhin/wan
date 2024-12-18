import { webview } from '@kit.ArkWeb'
import Logger from '../../utils/Logger';

@Builder
export function WebPageBuilder() {
  WebPage()
}

@Component
struct WebPage {
  controller: webview.WebviewController = new webview.WebviewController()
  // 子页面中获取全局的NavPathStack
  pathStack: NavPathStack = AppStorage.get("PathStack") as NavPathStack
  @State url: string = ""
  @State pageTitle: string = ""

  aboutToAppear(): void {
    this.url = this.pathStack.getParamByIndex(1) as string
  }

  build() {
    NavDestination() {
      Column() {
        Web({ src: this.url, controller: this.controller, renderMode: RenderMode.ASYNC_RENDER, })
          .layoutMode(WebLayoutMode.FIT_CONTENT)
          .onErrorReceive((event: OnErrorReceiveEvent) => {
            Logger.error(event.error.getErrorInfo())
          })
          .onAppear(() => {
            // 指定第二个参数为true，代表要进行预连接，如果为false该接口只会对网址进行dns预解析
            // 第三个参数为要预连接socket的个数。最多允许6个。
            webview.WebviewController.prepareForPageLoad(this.url, true, 2);
          })
          .fileAccess(true)
          .domStorageAccess(true)
          .geolocationAccess(true)
          .javaScriptAccess(true)
          .width('100%')
          .onPageBegin(() => {
          })
          .onPageEnd(() => {
          })
          .onTitleReceive((event: OnTitleReceiveEvent) => {
            this.pageTitle = event.title
          })

      }
      .height('100%')
      .width('100%')
    }
    .title(this.pageTitle)
    .onWillAppear(() => {
      this.url = this.pathStack.getParamByIndex(0) as string
    })
    .onReady((context: NavDestinationContext) => {
      Logger.info('current page config info is ' + JSON.stringify(context.getConfigInRouteMap()));
    })
  }
}