package com.dingstock.net.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseResp<T>(
    val data:T,
    val errCode:Int = 0,
    var errMsg:String = ""
)