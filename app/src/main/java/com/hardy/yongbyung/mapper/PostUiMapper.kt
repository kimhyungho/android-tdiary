package com.hardy.yongbyung.mapper

import com.hardy.domain.model.Post
import com.hardy.yongbyung.model.PostUiModel
import com.hardy.yongbyung.utils.DateUtil

object PostUiMapper: Mapper<Post, PostUiModel> {
    override fun mapToView(from: Post): PostUiModel {
        return PostUiModel(
            from.id,
            from.title,
            from.uid,
            from.story,
            from.createdAt,
            from.place,
            from.date,
            from.mediaUrl,
            DateUtil.dateToString(from.date)
        )
    }
}