package com.hardy.data.remote.interceptors

import android.content.Context
import com.google.auth.oauth2.GoogleCredentials
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class FcmAuthInterceptor @Inject constructor(
    @ApplicationContext
    private val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder().apply {
            addHeader("Authorization", "$Authorization ${getAccessToken()}")
        }
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    private fun getAccessToken(): String {
        val credentials =
            GoogleCredentials.fromStream(context.resources.assets.open("yongbyung-b2aa8-47f43fad346f.json"))
                .createScoped("https://www.googleapis.com/auth/firebase.messaging")
        return credentials.refreshAccessToken().tokenValue
    }

    companion object {
        const val Authorization = "Bearer"
    }
}