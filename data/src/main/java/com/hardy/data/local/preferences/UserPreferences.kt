package com.hardy.data.local.preferences

import com.hardy.domain.model.User
import com.hardy.yongbyung.datastore.UserPreferences

fun UserPreferences.toDomain(): User.Registered {
    return User.Registered(
        this.nickname,
        this.profileImage
    )
}