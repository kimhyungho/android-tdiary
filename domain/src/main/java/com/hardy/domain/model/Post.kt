package com.hardy.domain.model

import java.util.Date

data class Post(
    val id: String? = null,
    val uid: String? = null,
    val content: String? = null,
    val createdAt: Date? = null,
    val place: Place? = null,
    val date: Date? = null,
    val mediaUrl: String? = null,
)