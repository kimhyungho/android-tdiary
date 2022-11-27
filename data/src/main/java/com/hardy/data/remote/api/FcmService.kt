package com.hardy.data.remote.api

import com.hardy.data.remote.model.request.PostFcmSendRequest
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface FcmService {
    @POST("/v1/projects/yongbyung-b2aa8/messages:send")
    suspend fun postFcmSend(
        @Body body: PostFcmSendRequest
    ): ResponseBody
}