package com.hardy.domain.model

import java.util.Date

data class Message(
    val uid: String? = null,
    val message: String? = null,
    val createdAt: Date? = null,
    val readUsers: Map<String, Boolean>? = hashMapOf()
)