package com.hardy.domain.repositories

import com.hardy.domain.model.Message
import com.hardy.domain.model.MessageRoom
import com.hardy.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun sendMessage(receiverUid: String, message: String): Flow<Response<Unit>>

    fun getMessageRooms(): Flow<Response<List<Pair<String?, MessageRoom?>>>>

    fun getMessages(messageRoomId: String) : Flow<Response<List<Pair<String?, Message?>>>>
}