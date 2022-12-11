package com.hardy.yongbyung.di

import com.hardy.yongbyung.provider.permission.PermissionProvider
import com.hardy.yongbyung.provider.permission.PermissionProviderImpl
import com.hardy.yongbyung.provider.resource.ResourceProvider
import com.hardy.yongbyung.provider.resource.ResourceProviderImpl
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

    @Singleton
    @Binds
    fun bindPermissionProvider(
        provider: PermissionProviderImpl
    ): PermissionProvider
}