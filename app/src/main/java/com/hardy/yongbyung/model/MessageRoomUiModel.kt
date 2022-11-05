package com.hardy.yongbyung.model

import com.hardy.domain.model.Message
import com.hardy.domain.model.MessageRoom
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.AuthRepository
import com.hardy.domain.repositories.UserRepository
import com.hardy.yongbyung.utils.DateUtil
import kotlinx.coroutines.flow.last
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

data class MessageRoomUiModel(
    val id: String,
    val opponentUid: String,
    val opponentNickname: String,
    val lastMessage: String,
    val lastMessageDateString: String,
    val hasNewMessage: Boolean,
    val lastMessageDate: Date?
)

@Singleton
class MessageRoomUiMapper @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    private val myUid = authRepository.uid

    private fun getOpponentUid(users: Map<String, Boolean>?): String? {
        return users?.keys?.firstOrNull { it != myUid }
    }

    private suspend fun getOpponentNickname(opponentUid: String?): String? {
        opponentUid ?: return null
        val response = userRepository.getUser(opponentUid).last()
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
        val opponentUid = getOpponentUid(from.second?.users)
        return MessageRoomUiModel(
            id = from.first ?: "",
            opponentUid = opponentUid ?: "",
            opponentNickname = getOpponentNickname(opponentUid) ?: "",
            lastMessage = lastMessage?.message ?: "",
            lastMessageDateString = DateUtil.dateToAgoString(lastMessage?.createdAt) ?: "",
            hasNewMessage = getHasNewMessage(lastMessage),
            lastMessageDate = lastMessage?.createdAt
        )
    }
}