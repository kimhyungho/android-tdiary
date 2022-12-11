package com.hardy.data.di

import com.google.gson.Gson
import com.hardy.data.BuildConfig
import com.hardy.data.di.qualifiers.FcmApiQualifier
import com.hardy.data.di.qualifiers.KakaoApiQualifier
import com.hardy.data.remote.api.FcmService
import com.hardy.data.remote.api.KakaoService
import com.hardy.data.remote.interceptors.FcmAuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideOkHttp(
        authInterceptor: FcmAuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .addInterceptor(authInterceptor)
            .build()
    }

    @Singleton
    @Provides
    @FcmApiQualifier
    fun provideFcmRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fcm.googleapis.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideFcmService(
        @FcmApiQualifier
        retrofit: Retrofit
    ): FcmService = retrofit.create(FcmService::class.java)

    @Singleton
    @Provides
    @KakaoApiQualifier
    fun provideKakaoRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideKakaoService(
        @KakaoApiQualifier
        retrofit: Retrofit
    ): KakaoService = retrofit.create(KakaoService::class.java)

}