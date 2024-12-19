package com.dingstock.core.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HotkeyEntity(
    val id: Int, val link: String, val name: String, val order: Int, val visible: Int
)