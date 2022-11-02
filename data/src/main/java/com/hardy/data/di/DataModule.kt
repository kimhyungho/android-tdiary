package com.hardy.data.di

import com.hardy.data.repositories.AuthRepositoryImpl
import com.hardy.data.repositories.PostRepositoryImpl
import com.hardy.data.repositories.UserRepositoryImpl
import com.hardy.domain.repositories.AuthRepository
import com.hardy.domain.repositories.PostRepository
import com.hardy.domain.repositories.UserRepository
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
}