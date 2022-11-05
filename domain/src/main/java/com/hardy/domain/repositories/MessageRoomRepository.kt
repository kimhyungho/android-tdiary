package com.hardy.domain.repositories

import com.hardy.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface MessageRoomRepository {
    fun deleteMessageRoom(messageRoomId: String): Flow<Response<Unit>>
}