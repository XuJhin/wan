package com.dingstock.wancompose.di

import com.dingstock.net.api.WanApi
import com.dingstock.wancompose.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ResourceModule {
    @Singleton
    @Provides
    fun providerRepository(wanApi: WanApi): Repository {
        return Repository(wanApi)
    }
}