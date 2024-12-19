@Component
struct ProgramPage {
  build() {
    Column() {
      Text("这是项目中心页面")
        .fontSize(24)
        .padding(20)
      // 你可以在这里添加其他的页面内容
    }
    .width('100%')
    .height('100%')
  }
}
export default ProgramPage;