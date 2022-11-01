package com.hardy.domain.repositories

import com.hardy.domain.model.Response
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface PostRepository {
    fun writePost(
        category: String,
        title: String,
        content: String,
        date: Date,
        mainRegion: String,
        subRegion: String,
        location: String?
    ): Flow<Response<String>>
}