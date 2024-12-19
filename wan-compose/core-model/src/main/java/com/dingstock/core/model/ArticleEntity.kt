package com.dingstock.core.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticleEntity(
    val id: Int,
    val link: String,
    val niceDate: String,
    val niceShareDate: String,
    val publishTime: Long,
    val shareDate: Long,
    val title: String,
    val author: String,
)