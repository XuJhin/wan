package com.dingstock.net.di

import com.dingstock.net.api.WanApi
import com.dingstock.net.createOkHttpClient
import com.dingstock.net.createRetrofit
import com.dingstock.net.model.Env
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return createOkHttpClient()
    }

    @Provides
    @Singleton
    fun providerEnv(): Env {
        return Env.Debug("https://www.wanandroid.com")
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, env: Env): Retrofit {
        return createRetrofit(okHttpClient, env)
    }

    @Provides
    @Singleton
    fun providerService(retrofit: Retrofit): WanApi {
        return retrofit.create(WanApi::class.java)
    }
}