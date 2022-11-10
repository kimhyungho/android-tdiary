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
    ): PostRepository

    @Singleton
    @Binds
    fun bindUserRepository(
        repo: UserRepositoryImpl
    ): UserRepository

    @Singleton
    @Binds
    fun bindMessageRepository(
        repo: MessageRepositoryImpl
    ): MessageRepository

    @Singleton
    @Binds
    fun bindMessageRoomRepository(
        repo: MessageRoomRepositoryImpl
    ): MessageRoomRepository

    @Singleton
    @Binds
    fun bindReportRepository(
        repo: ReportRepositoryImpl
    ): ReportRepository
}