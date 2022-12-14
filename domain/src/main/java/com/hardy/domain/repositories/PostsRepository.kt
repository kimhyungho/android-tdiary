package com.hardy.domain.repositories

import com.hardy.domain.model.Place
import com.hardy.domain.model.Post
import com.hardy.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
    fun getPostsFromFirestore(): Flow<Response<List<Post>>>

    fun getPostsByUidFromFirestore(uid: String?): Flow<Response<List<Post>>>

    suspend fun addPostToFirestore(
        title: String,
        content: String,
        place: Place,
        mediaUri1: String?,
        mediaUri2: String?,
        mediaUri3: String?
    ): Flow<Response<Void?>>

    suspend fun deletePostFromFirestore(postId: String): Flow<Response<Void?>>
}