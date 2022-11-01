package com.hardy.yongbyung.di

import com.hardy.yongbyung.provider.ResourceProvider
import com.hardy.yongbyung.provider.ResourceProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ProviderModule {
    @Singleton
    @Binds
    fun bindResourceProvider(
        provider: ResourceProviderImpl
    ): ResourceProvider
}