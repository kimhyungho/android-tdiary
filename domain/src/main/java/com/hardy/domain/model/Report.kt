package com.hardy.domain.model

import java.util.Date

data class Report(
    val reporterUid: String? = null,
    val postId: String? = null,
    val title: String? = null,
    val content: String? = null,
    val createdAt: Date? = null
)