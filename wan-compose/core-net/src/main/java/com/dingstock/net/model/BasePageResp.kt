package com.dingstock.net.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BasePageResp<T>(
    val curPage: Int,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int,
    val datas: MutableList<T> = mutableListOf()
)