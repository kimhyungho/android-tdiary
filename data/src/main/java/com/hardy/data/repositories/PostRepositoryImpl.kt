package com.hardy.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hardy.domain.model.Post
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
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
        location: String
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
                location = location,
                createdAt = Date(),
                isRecruiting = true
            )
            firestore.collection("posts").add(post).await().run {
                emit(Response.Success(id))
            }
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }
}