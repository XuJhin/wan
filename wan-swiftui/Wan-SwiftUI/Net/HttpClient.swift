//
//  HttpClient.swift
//  Wan-SwiftUI
//
//  Created by dingstock on 2024/12/18.
//

import Foundation
import Alamofire

enum NetworkResult<T> {
    case success(T)
    case failure(Int, String)
}

class NetworkManager {
    private var session: Session

    init(session: Session = Session.default) {
        self.session = session
    }

    // 通用的网络请求方法
    func request<T: Decodable>(
        url: String,
        responseType: T.Type,
        completion: @escaping (NetworkResult<T>) -> Void
    ) {
        session.request(url)
            .validate()
            .responseDecodable(of: Resp<T>.self) { response in
                switch response.result {
                case let .success(resp):
                    if resp.errorCode == 0 {
                        // 请求成功，返回实际数据
                        DispatchQueue.main.async {
                            completion(.success(resp.data))
                        }
                    } else {
                        // 错误码不为0，返回失败
                        DispatchQueue.main.async {
                            completion(.failure(resp.errorCode, "Error code: \(resp.errorCode), Message: \(resp.errorCode)"))
                        }
                    }
                case let .failure(error):
                    // 网络请求失败
                    DispatchQueue.main.async {
                        completion(.failure(400, "Request failed: \(error.localizedDescription)"))
                    }
                }
            }
    }
}
