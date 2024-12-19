package com.dingstock.net.api

import com.dingstock.core.model.ArticleEntity
import com.dingstock.core.model.BannerEntity
import com.dingstock.core.model.HotkeyEntity
import com.dingstock.net.model.BasePageResp
import retrofit2.http.GET
import retrofit2.http.Path

interface WanApi {

    @GET("banner/json")
    suspend fun banner(): MutableList<BannerEntity>

    @GET("article/list/{page}/json")
    suspend fun articles(@Path("page") page: Int): BasePageResp<ArticleEntity>

    @GET("article/top/json")
    suspend fun topArticles(): MutableList<ArticleEntity>

    @GET("hotkey/json")
    suspend fun hotKeys(): MutableList<HotkeyEntity>
}