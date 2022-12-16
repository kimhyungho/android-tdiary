package com.hardy.domain.repositories

import android.net.Uri
import com.hardy.domain.model.Place
import com.hardy.domain.model.Post
import com.hardy.domain.model.Response
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface PostsRepository {
    fun getPostsFromFirestore(): Flow<Response<List<Post>>>

    suspend fun addPostToFirestore(
        date: Date,
        title: String,
        place: Place?,
        story: String,
        mediaUri: Uri?,
        mimeType: String?
    ): Flow<Response<Void?>>

    suspend fun deletePostFromFirestore(postId: String): Flow<Response<Void?>>
}