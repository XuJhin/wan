package com.dingstock.core.model

import com.squareup.moshi.JsonClass

/**
 * Banner
 */
@JsonClass(generateAdapter = true)
data class BannerEntity(
    val desc: String, val id: Int, val imagePath: String, val isVisible: Int, val type: Int, val url: String
)
