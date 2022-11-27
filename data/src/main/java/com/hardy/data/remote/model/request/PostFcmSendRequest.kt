package com.hardy.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class PostFcmSendRequest(
    @SerializedName("message")
    val message: FcmMessage
) {
    data class FcmMessage(
        @SerializedName("token")
        val token: String,
        @SerializedName("notification")
        val notification: Notification
    ) {
        data class Notification(
            @SerializedName("title")
            val title: String,
            @SerializedName("body")
            val body: String
        )
    }
}