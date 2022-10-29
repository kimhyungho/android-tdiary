package com.hardy.domain.model

sealed class User {
    object UnRegistered : User()
    data class Registered(
        val nickname: String? = null,
        val profileImage: String? = null
    ) : User()
}