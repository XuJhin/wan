import axios, { Axios, AxiosError, AxiosRequestConfig, AxiosResponse } from '@ohos/axios'

class ApiService {
  private BASE_URL = "https://www.wanandroid.com/"
  private axiosInstance = axios.create({
    baseURL: this.BASE_URL,
    timeout: 15000,
    headers: {
      'Content-Type': 'application/json',
    }
  })

  constructor() {
    this.setupApiClient()
  }

  private setupApiClient() {
    this.axiosInstance.interceptors.response.use((response: AxiosResponse) => {
      if (response.data.errorCode != 0) {
        return Promise.reject({
          err: response.data.errorCode,
          msg: response.data.errorMsg
        })
      }
      return {
        status: response.status,
        statusText: response.statusText,
        headers: response.headers,
        config: response.config,
        request: response.request,
        performanceTiming: response.performanceTiming,
        data: response.data.data
      }

    }, (err: AxiosError) => {
      return Promise.reject(err)
    })

  }

  // 通用 GET 请求封装
  public async get<T>(url: string, param?: string | Object | ArrayBuffer): Promise<T> {
    try {
      const response: AxiosResponse<T> = await this.axiosInstance.get(url, { params: param });
      return response.data; // 返回数据部分
    } catch (error) {
      this.handleError(error); // 统一处理错误
      return error
    }
  }

  // 通用 POST 请求封装
  public async post<T>(url: string, config?: AxiosRequestConfig): Promise<T> {
    try {
      const response: AxiosResponse<T> = await this.axiosInstance.post(url, config);
      return response.data; // 返回数据部分
    } catch (error) {
      this.handleError(error); // 统一处理错误
      return error
    }
  }

  // 错误处理方法，可以在这里统一处理不同类型的错误
  private handleError(error: AxiosError) {
    if (axios.isAxiosError(error)) {
      // 网络请求错误
      console.error('Axios error:', error.response?.data || error.message);
    } else {
      // 其他类型的错误
      console.error('Unexpected error:', error);
    }
  }
}

// 创建单例服务实例，方便全局调用
export const apiService = new ApiService();