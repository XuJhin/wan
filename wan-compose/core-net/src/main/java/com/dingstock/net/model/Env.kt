package com.dingstock.net.model

sealed interface Env {
    val baseUrl:String
    class Debug(override val baseUrl: String) : Env {}
    class Release(override val baseUrl: String) : Env {}
    class Pre(override val baseUrl: String) : Env {}
}