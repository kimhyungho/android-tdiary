package com.hardy.domain.model

import java.util.Date

sealed class User {
    object UnRegistered : User()
    data class Registered(
        val nickname: String? = null,
        val profileImage: String? = null,
        val createdAt: Date? = null
    ) : User()
}