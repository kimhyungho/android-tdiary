package com.hardy.data.repositories

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.hardy.data.di.qualifiers.PostsQualifier
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
    @PostsQualifier
    private val postsRef: CollectionReference,
    private val firebaseStorage: FirebaseStorage
) : PostsRepository {
    private val uid get() = firebaseAuth.currentUser?.uid

    override fun getPostsFromFirestore(): Flow<Response<List<Post>>> = callbackFlow {
        val snapshotListener = postsRef.orderBy("createdAt", Query.Direction.DESCENDING)
            .whereEqualTo("uid", uid)
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
        date: Date,
        title: String,
        place: Place?,
        story: String,
        mediaUri: Uri?,
        mimeType: String?
    ): Flow<Response<Void?>> = flow {
        try {
            emit(Response.Loading)
            val id = postsRef.document().id
            val ref = firebaseStorage.reference.child("images/posts/${id}/1.$mimeType")
            if (mediaUri != null) {
                ref.putFile(mediaUri).await()
            }
            val post = Post(
                title = title,
                id = id,
                uid = uid,
                story = story,
                createdAt = Date(),
                place = place,
                date = date,
                mediaUrl = if (mediaUri == null) null else ref.downloadUrl.await().toString()
            )
            postsRef.document(id).set(post).await()
            emit(Response.Success(null))
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