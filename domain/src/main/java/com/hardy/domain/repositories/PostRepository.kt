package com.hardy.domain.repositories

import androidx.paging.PagingData
import com.hardy.domain.model.Post
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

    fun getPosts(
        category: String,
        mainRegion: String,
        subRegion: String
    ): Flow<PagingData<Pair<String, Post>>>

    fun getPosts(uid: String): Flow<PagingData<Pair<String, Post>>>

    fun getPost(postId: String): Flow<Response<Pair<String, Post>>>

    fun finishRecruit(postId: String) : Flow<Response<Unit>>
}