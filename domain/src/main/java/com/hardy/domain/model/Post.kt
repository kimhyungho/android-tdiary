package com.hardy.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Post(
    val id: String? = null,
    val title: String? = null,
    val uid: String? = null,
    val story: String? = null,
    val createdAt: Date? = null,
    val place: Place? = null,
    val date: Date? = null,
    val mediaUrl: String? = null
) : Parcelable