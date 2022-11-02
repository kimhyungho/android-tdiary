package com.hardy.domain.model

import java.util.Date

data class Post(
    val category: String? = null,
    val title: String? = null,
    val uid: String? = null,
    val content: String? = null,
    val date: Date? = null,
    val mainRegion: String? = null,
    val subRegion: String? = null,
    val location: String? = null,
    val createdAt: Date? = null,
    val recruiting: Boolean? = null
)