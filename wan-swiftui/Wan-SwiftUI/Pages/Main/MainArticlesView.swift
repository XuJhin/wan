//
//  ContentView.swift
//  Wan-SwiftUI
//
//  Created by dingstock on 2024/12/17.
//

import Alamofire
import SwiftUI

struct ListItem: View {
    var item: Article

    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text(item.getTitle())
                .font(.system(size: 17))
            HStack(spacing: 20) {
                Text(item.author)
                    .font(.system(size: 15))
                    .foregroundColor(.gray)
                Spacer()
                Text(item.niceShareDate)
                    .font(.system(size: 15))
                    .foregroundColor(.gray)
            }
        }
        .padding(EdgeInsets(top: 10, leading: 0, bottom: 10, trailing: 0))
    }
}

struct ContentView: View {
    @StateObject private var viewModel = MainViewModel()

    var body: some View {
        NavigationView {
            VStack {
                if viewModel.isEmpty {
                    Text("没有数据")
                        .font(.title)
                        .foregroundColor(.gray)
                        .padding()
                    Spacer()
                } else {
                    List(viewModel.items) { item in
                        ListItem(item: item)
                            .onAppear {
                                // 检测当前 item 是否出现在屏幕上，如果是最后一个 item，触发加载更多
                                if item.id == viewModel.items.last?.id {
                                    viewModel.loadData() // 触发加载更多
                                }
                            }
                    }
                    .frame(maxWidth: .infinity)
                    .listRowInsets(EdgeInsets())
                    .overlay(
                        Group {
                            if viewModel.isLoading {
                                ProgressView()
                                    .progressViewStyle(CircularProgressViewStyle())
                                    .padding()
                            }
                        }, alignment: .bottom
                    )
                }
            }
            .navigationBarTitle("文章")
            .onAppear {
                // 首次加载数据
                viewModel.loadData()
            }
            .frame(maxWidth: .infinity)
        }
    }
}

#Preview {
    ContentView()
}
