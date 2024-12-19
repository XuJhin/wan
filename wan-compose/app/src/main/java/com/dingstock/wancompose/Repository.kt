package com.dingstock.wancompose

import com.dingstock.net.api.WanApi
import javax.inject.Inject

class Repository @Inject constructor(private val wanApi: WanApi) {

    suspend fun articles(page: Int) = wanApi.articles(page)
    suspend fun banner() = wanApi.banner()
    suspend fun topArticles() = wanApi.topArticles()
}