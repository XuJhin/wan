@Component
struct SquarePage {
  build() {
    Column() {
      Text("这是广场页面")
        .fontSize(24)
        .padding(20)
      // 你可以在这里添加其他的页面内容
    }
    .width('100%')
    .height('100%')
  }
}

export default SquarePage;