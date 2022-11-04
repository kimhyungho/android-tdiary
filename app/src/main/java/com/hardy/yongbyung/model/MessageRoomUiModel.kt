package com.hardy.yongbyung.model

import android.util.Log
import com.hardy.domain.model.Message
import com.hardy.domain.model.MessageRoom
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.AuthRepository
import com.hardy.domain.repositories.UserRepository
import com.hardy.yongbyung.mapper.Mapper
import com.hardy.yongbyung.utils.DateUtil
import kotlinx.coroutines.flow.first
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

data class MessageRoomUiModel(
    val id: String,
    val opponentNickname: String,
    val lastMessage: String,
    val lastMessageDate: String,
    val hasNewMessage: Boolean
)

@Singleton
class MessageRoomUiMapper @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    private val myUid = authRepository.uid

    private suspend fun getOpponentNickname(users: Map<String, Boolean>?): String? {
        val opponentUserUid = users?.keys?.firstOrNull { it != myUid } ?: return null
        val response = userRepository.getUser(opponentUserUid).first()
        if (response !is Response.Success) return null
        return response.data?.nickname
    }

    private fun getLastMessage(messages: Map<String, Message>?): Message? {
        return messages?.map { it.value }?.maxByOrNull { it.createdAt ?: Date(0L) }
    }

    private fun getHasNewMessage(lastMessage: Message?): Boolean {
        return !(lastMessage?.readUsers?.containsKey(myUid) ?: true)
    }

    suspend fun mapToView(from: Pair<String?, MessageRoom?>): MessageRoomUiModel {
        val lastMessage = getLastMessage(from.second?.messages)
        return MessageRoomUiModel(
            id = from.first ?: "",
            opponentNickname = getOpponentNickname(from.second?.users) ?: "",
            lastMessage = lastMessage?.message ?: "",
            lastMessageDate = DateUtil.dateToString(lastMessage?.createdAt, DateUtil.DOTTED_FORMAT)
                ?: "",
            hasNewMessage = getHasNewMessage(lastMessage)
        )
    }
}