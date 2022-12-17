package com.hardy.yongbyung.model

import com.hardy.domain.model.Place
import com.hardy.domain.model.Post
import com.hardy.yongbyung.utils.DateUtil
import java.util.*

data class PostUiModel(
    val id: String? = null,
    val title: String? = null,
    val uid: String? = null,
    val story: String? = null,
    val createdAt: Date? = null,
    val place: Place? = null,
    val date: Date? = null,
    val mediaUrl: String? = null,
    val stringDate: String? = DateUtil.dateToString(date)
) {
    fun toDomain(): Post {
        return Post(id, title, uid, story, createdAt, place, date, mediaUrl)
    }
}