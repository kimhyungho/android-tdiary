package com.hardy.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.FirebaseStorage
import com.hardy.domain.model.Place
import com.hardy.domain.model.Post
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.PostsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val postsRef: CollectionReference,
    private val firebaseStorage: FirebaseStorage
) : PostsRepository {
    private val uid get() = firebaseAuth.currentUser?.uid

    override fun getPostsFromFirestore(): Flow<Response<List<Post>>> = callbackFlow {
        val snapshotListener = postsRef.orderBy("createdAt").addSnapshotListener { snapshot, e ->
            val response = if (snapshot != null) {
                val posts = snapshot.toObjects(Post::class.java)
                Response.Success(posts)
            } else {
                Response.Failure(e?.cause)
            }

            trySend(response).isSuccess
        }

        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun getPostsByUidFromFirestore(uid: String?): Flow<Response<List<Post>>> =
        callbackFlow {
            val snapshotListener = postsRef.orderBy("createdAt")
                .whereEqualTo("uid", uid ?: this@PostRepositoryImpl.uid)
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val posts = snapshot.toObjects(Post::class.java)
                        Response.Success(posts)
                    } else {
                        Response.Failure(e?.cause)
                    }

                    trySend(response).isSuccess
                }

            awaitClose {
                snapshotListener.remove()
            }
        }

    override suspend fun addPostToFirestore(
        title: String,
        content: String,
        place: Place,
        mediaUri1: String?,
        mediaUri2: String?,
        mediaUri3: String?
    ): Flow<Response<Void?>> = flow {
        try {
            emit(Response.Loading)
            val id = postsRef.document().id
            val ref = firebaseStorage.reference.child("images/${id}")
            if (mediaUri1 != null) ref.child("1")
            if (mediaUri2 != null) ref.child("2")
            if (mediaUri3 != null) ref.child("3")
            val post = Post(
                id = id,
                uid = uid,
                content = content,
                createdAt = Date(),
                place = place,
                mediaUri1,
                mediaUri2,
                mediaUri3
            )
            postsRef.document(id).set(post).await()
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }

    override suspend fun deletePostFromFirestore(postId: String): Flow<Response<Void?>> = flow {
        try {
            postsRef.document(postId).delete().await()
            Response.Success(true)
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }
}