package com.hardy.data.remote.interceptors

import android.content.Context
import com.google.auth.oauth2.GoogleCredentials
import com.hardy.data.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class KakaoAuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder().apply {
            addHeader("Authorization", "$Authorization ${BuildConfig.kakaomapapikey}")
        }
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    companion object {
        const val Authorization = "KakaoAK"
    }
}