package com.hardy.yongbyung.model

import androidx.annotation.DrawableRes
import com.hardy.domain.model.Post
import com.hardy.yongbyung.R
import com.hardy.yongbyung.mapper.Mapper
import com.hardy.yongbyung.utils.DateUtil

data class PostUiModel(
    val id: String,
    val isRecruiting: Boolean,
    @DrawableRes val categoryDrawableResId: Int,
    val category: String,
    val title: String,
    val content: String,
    val date: String,
    val mainRegion: String,
    val subRegion: String,
    val location: String?,
    val createdAt: String
)

object PostUiMapper : Mapper<Pair<String, Post>, PostUiModel> {
    private fun categoryToDrawableRes(category: String?): Int {
        return when (category) {
            "풋살" -> R.drawable.ic_football
            "축구" -> R.drawable.ic_soccer
            else -> R.drawable.ic_more
        }
    }

    override fun mapToView(from: Pair<String, Post>): PostUiModel {
        return PostUiModel(
            id = from.first,
            isRecruiting = from.second.recruiting ?: false,
            categoryDrawableResId = categoryToDrawableRes(from.second.category),
            category = from.second.category ?: "",
            title = from.second.title ?: "",
            content = from.second.content ?: "",
            date = DateUtil.dateToString(from.second.date, DateUtil.DOTTED_FORMAT) ?: "",
            mainRegion = from.second.mainRegion ?: "",
            subRegion = from.second.subRegion ?: "",
            location = from.second.location,
            createdAt = DateUtil.dateToAgoString(from.second.createdAt) ?: ""
        )
    }
}