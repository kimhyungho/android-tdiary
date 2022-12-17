package com.hardy.data.di

import com.hardy.data.repositories.*
import com.hardy.domain.repositories.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Singleton
    @Binds
    fun bindAuthRepository(
        repo: AuthRepositoryImpl
    ): AuthRepository

    @Singleton
    @Binds
    fun bindPostRepository(
        repo: PostRepositoryImpl
    ): PostsRepository

    @Singleton
    @Binds
    fun bindSettingRepository(
        repo: SettingRepositoryImpl
    ): SettingRepository

    @Singleton
    @Binds
    fun bindKakaoMapRepository(
        repo: KakaoMapRepositoryImpl
    ): KakaoMapRepository
}