package com.hardy.yongbyung.model

import com.hardy.domain.model.User
import com.hardy.yongbyung.mapper.Mapper
import com.hardy.yongbyung.utils.DateUtil
import com.hardy.yongbyung.utils.DateUtil.YEAR_MONTH_DAY_FORMAT

data class UserUiModel(
    val nickname: String,
    val profileImage: String,
    val createdAt: String
)

object UserUiMapper : Mapper<User.Registered, UserUiModel> {
    override fun mapToView(from: User.Registered): UserUiModel {
        return UserUiModel(
            nickname = from.nickname ?: "",
            profileImage = from.profileImage ?: "",
            createdAt = DateUtil.dateToString(from.createdAt, YEAR_MONTH_DAY_FORMAT) ?: ""
        )
    }
}