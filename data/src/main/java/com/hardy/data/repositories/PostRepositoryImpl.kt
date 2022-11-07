package com.hardy.data.repositories

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.hardy.domain.model.Post
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import okio.IOException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : PostRepository {
    override fun writePost(
        category: String,
        title: String,
        content: String,
        date: Date,
        mainRegion: String,
        subRegion: String,
        location: String?
    ): Flow<Response<String>> = flow {
        try {
            emit(Response.Loading)
            val uid = firebaseAuth.currentUser?.uid ?: throw IllegalArgumentException("not sign in")
            val post = Post(
                category = category,
                title = title,
                uid = uid,
                content = content,
                date = date,
                mainRegion = mainRegion,
                subRegion = subRegion,
                location = location,
                createdAt = Date(),
                recruiting = true
            )
            firestore.collection("posts").add(post).await().run {
                emit(Response.Success(id))
            }
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }

    override fun getPosts(
        category: String,
        mainRegion: String,
        subRegion: String
    ): Flow<PagingData<Pair<String, Post>>> = Pager(PagingConfig(20)) {
        val query = if (category == "전체" && mainRegion == "전국" && subRegion == "전체") {
            firestore.collection("posts")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .limit(20L)
        } else if (category == "전체") {
            firestore.collection("posts")
                .whereEqualTo("mainRegion", mainRegion)
                .whereEqualTo("subRegion", subRegion)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .limit(20L)
        } else if (mainRegion == "전국" && subRegion == "전체") {
            firestore.collection("posts")
                .whereEqualTo("category", category)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .limit(20L)
        } else {
            firestore.collection("posts")
                .whereEqualTo("category", category)
                .whereEqualTo("mainRegion", mainRegion)
                .whereEqualTo("subRegion", subRegion)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .limit(20L)
        }
        GetPostPagingSource(query)
    }.flow

    override fun getPosts(uid: String): Flow<PagingData<Pair<String, Post>>> =
        Pager(PagingConfig(20)) {
            val query = firestore.collection("posts")
                .whereEqualTo("uid", uid)
                .orderBy("createdAt", Query.Direction.DESCENDING)
            GetPostPagingSource(query)
        }.flow

    override fun getPost(postId: String): Flow<Response<Pair<String, Post>>> = flow {
        try {
            emit(Response.Loading)
            val snapshot = firestore.collection("posts").document(postId).get().await()
            val post = snapshot.toObject(Post::class.java) ?: throw IOException("post not exist")
            emit(Response.Success(Pair(snapshot.id, post)))
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }

    override fun finishRecruit(postId: String): Flow<Response<Unit>> = flow {
        try {
            emit(Response.Loading)
            firestore.collection("posts").document(postId).update("recruiting", false).await()
            emit(Response.Success(Unit))
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }
}