//
//  MainViewModel.swift
//  Wan-SwiftUI
//
//  Created by dingstock on 2024/12/18.
//

import Foundation

class MainViewModel: ObservableObject {
    @Published var items: [Article] = []
    @Published var isLoading: Bool = false
    @Published var hasMoreData: Bool = true
    @Published var isEmpty: Bool = false

    private var currentPage = 0
    private let pageSize = 20
    private var url = "https://www.wanandroid.com/article/list/"

    func loadData() {
        guard !isLoading && hasMoreData else { return }

        isLoading = true
        let requestURL = "\(url)\(currentPage)/json"

        // 请求数据
        NetworkManager()
            .request(url: requestURL, responseType: PageRep<Article>.self) { [weak self] result in
                guard let self = self else { return }
                
                DispatchQueue.main.async {
                    switch result {
                    case let .success(data):
                        if self.currentPage == 0 {
                            self.items = data.datas // 初次加载数据
                        } else {
                            self.items.append(contentsOf: data.datas) // 加载更多数据
                        }

                        // 更新是否有更多数据
                        self.hasMoreData = data.datas.count > 0
                        self.isEmpty = self.items.isEmpty
                        self.isLoading = false

                        // 更新分页
                        if self.hasMoreData {
                            self.currentPage += 1
                        }
                    case let .failure(_, error):
                        print("请求失败: \(error)")
                        self.isLoading = false
                    }
                }
            }
    }
}
