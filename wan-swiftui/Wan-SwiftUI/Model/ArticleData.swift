//
//  ArticleData.swift
//  Wan-SwiftUI
//
//  Created by dingstock on 2024/12/17.
//

import Foundation

// 基本响应结构
class Resp<T: Decodable>: Decodable {
    let errorMsg: String
    let errorCode: Int
    let data: T
}

// 分页响应结构
class PageRep<T: Decodable>: Decodable {
    let datas:[Article]
    let curPage:Int
    let pageCount:Int
    let offset:Int
}

struct Article: Decodable,Hashable,Identifiable {
    let id: Int
    let title: String
    let authorName:String?
    let author:String
    let link:String
    let niceShareDate:String
    
    
    
    func getTitle()->AttributedString{
        if let attributedString = try? AttributedString(markdown: title) {
            return attributedString
        } else {
            return AttributedString(title)
        }
    }
}
