package com.hardy.domain.repositories

import com.hardy.domain.model.Message
import com.hardy.domain.model.MessageRoom
import com.hardy.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun sendMessage(receiverUid: String, message: String): Flow<Response<String>>

    fun getMessages(messageRoomId: String) : Flow<Response<List<Pair<String?, Message?>>>>
}