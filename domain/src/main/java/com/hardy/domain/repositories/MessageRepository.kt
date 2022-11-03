package com.hardy.domain.repositories

import com.hardy.domain.model.MessageRoom
import com.hardy.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun sendMessage(receiverUid: String, message: String)

    fun getMessageRooms(): Flow<Response<List<Pair<String?, MessageRoom?>>>>
}