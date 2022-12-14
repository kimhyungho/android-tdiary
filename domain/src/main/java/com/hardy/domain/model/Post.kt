package com.hardy.domain.model

import java.util.Date

data class Post(
    val id: String? = null,
    val uid: String? = null,
    val content: String? = null,
    val createdAt: Date? = null,
    val place: Place? = null,
    val mediaUrl1: String?,
    val mediaUrl2: String?,
    val mediaUrl3: String?
)