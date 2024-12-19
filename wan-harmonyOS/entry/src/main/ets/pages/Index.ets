import MinePage from './Mine';
import ProgramPage from './Program';
import SquarePage from './Square';
import { componentUtils } from '@kit.ArkUI';
import HomePage from './home/HomePage';

class TabItem {
  public id: string;
  public title: string;
  public icon: string;

  constructor(id: string, title: string) {
    this.id = id
    this.title = title
    this.icon = ""
  }
}

@Builder
export function IndexRouterBuilder() {
  Index()
}

@Entry
@Component
struct Index {
  pathStack: NavPathStack = new NavPathStack()
  @State animationDuration: number = 300;
  @State indicatorLeftMargin: number = 0;
  @State indicatorWidth: number = 0;
  @StorageProp('currentBreakpoint')
  currentBreakpoint: string = 'sm';
  @State currentPageIndex: number = 0;
  @State tabList: Array<TabItem> = [
    new TabItem("home", "首页"),
    new TabItem("program", "项目"),
    new TabItem("square", "广场"),
    new TabItem("mine", "我的")
  ];
  private tabsController = new TabsController();

  // 切换页面通用方法
  private changePage(index: number): void {
    this.currentPageIndex = index;
    this.tabsController.changeIndex(index);
  }

  onPageShow(): void {
  }

  onPageHide(): void {
  }

  onBackPress(): boolean | void {
  }

  // 全局设置一个NavPathStack
  aboutToAppear(): void {
    AppStorage.setOrCreate("PathStack", this.pathStack)
  }

  // 动态构建 TabBar
  @Builder
  TabBuilder(title: string, targetIndex: number) {
    NavDestination() {

    }

  }

  build() {
    Navigation(this.pathStack) {
      Column() {
        Tabs({
          barPosition: BarPosition.End,
          controller: this.tabsController,
        }) {
          ForEach(this.tabList, (item: TabItem, index: number) => {
            TabContent() {
              this.newLocalBuilder(index)
            }.tabBar(this.TabBuilder(item.title, index));
          }, (item: TabItem, index: number) => {
            return item.id
          });
        }
        .onAnimationStart((index, targetIndex, event) => {
          this.currentPageIndex = targetIndex
          let targetIndexInfo = this.getTextInfo(targetIndex)
          this.startAnimateTo(this.animationDuration, targetIndexInfo.left, targetIndexInfo.width);
        })
        .onChange((index: number) => {
          this.changePage(index); // 监听页面滑动事件
        })
        .vertical(false);

      }
      .width('100%')
      .height('100%');
    }
    .size({ width: '100%', height: '100%' })
    .mode(NavigationMode.Stack)
    .hideTitleBar(true)

  }

  startAnimateTo(duration: number, leftMargin: number, width: number) {
    animateTo({
      // 动画时长
      duration: duration,
      // 动画曲线
      curve: Curve.Linear,
      // 播放次数
      iterations: 1,
      // 动画模式
      playMode: PlayMode.Normal,
      onFinish: () => {
        console.info('play end');
      }
    }, () => {
      this.indicatorLeftMargin = leftMargin;
      this.indicatorWidth = width;
    })
  }

  // 获取组件大小、位置、平移缩放旋转及仿射矩阵属性信息。
  private getTextInfo(index: number): Record<string, number> {
    let modePosition: componentUtils.ComponentInfo = componentUtils.getRectangleById(index.toString());
    return { 'left': px2vp(modePosition.windowOffset.x), 'width': px2vp(modePosition.size.width) };
  }

  @Builder
  newLocalBuilder(index: number) {
    if (index == 0) {
      HomePage();
    } else if (index == 1) {
      ProgramPage();
    } else if (index == 2) {
      SquarePage();
    } else if (index == 3) {
      MinePage();
    } else {
      MinePage();
    }
  }
}
