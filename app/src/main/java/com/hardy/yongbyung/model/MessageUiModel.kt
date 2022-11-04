package com.hardy.yongbyung.model

import com.hardy.domain.model.Message
import com.hardy.domain.repositories.AuthRepository
import com.hardy.yongbyung.mapper.Mapper
import com.hardy.yongbyung.utils.DateUtil
import javax.inject.Inject
import javax.inject.Singleton


data class MessageUiModel(
    val id: String,
    val message: String,
    val createdAt: String,
    val viewType: Int
) {
    companion object ViewType {
        const val SEND_MESSAGE = 0
        const val RECEIVE_MESSAGE = 1
    }
}

@Singleton
class MessageUiMapper @Inject constructor(
    private val authRepository: AuthRepository
) : Mapper<Pair<String?, Message?>, MessageUiModel> {
    val myUid = authRepository.uid
    override fun mapToView(from: Pair<String?, Message?>): MessageUiModel {
        return MessageUiModel(
            id = from.first ?: "",
            message = from.second?.message ?: "",
            createdAt = DateUtil.dateToAgoString(from.second?.createdAt) ?: "",
            viewType = if (from.second?.uid == myUid) MessageUiModel.SEND_MESSAGE
            else MessageUiModel.RECEIVE_MESSAGE
        )
    }
}