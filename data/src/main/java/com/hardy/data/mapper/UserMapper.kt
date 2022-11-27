package com.hardy.data.mapper

import com.hardy.domain.model.User
import com.hardy.yongbyung.datastore.UserPreferences
import java.util.Date

object UserMapper : Mapper<UserPreferences, User.Registered> {
    override fun mapToDomain(from: UserPreferences): User.Registered {
        return User.Registered(
            nickname = from.nickname,
            profileImage = from.profileImage,
            createdAt = Date(from.createdAt),
            fcmToken = from.fcmToken
        )
    }
}