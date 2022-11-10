package com.hardy.domain.repositories

import com.hardy.domain.model.MessageRoom
import com.hardy.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface MessageRoomRepository {
    fun deleteMessageRoom(messageRoomId: String): Flow<Response<Unit>>

    fun getMessageRooms(): Flow<Response<List<Pair<String?, MessageRoom?>>>>
}