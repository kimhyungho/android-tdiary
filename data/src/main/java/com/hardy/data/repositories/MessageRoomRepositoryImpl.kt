package com.hardy.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.MessageRoomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRoomRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : MessageRoomRepository {
    private val uid get() = firebaseAuth.currentUser?.uid

    override fun deleteMessageRoom(messageRoomId: String): Flow<Response<Unit>> = flow {
        try {
            emit(Response.Loading)
            uid ?: throw IllegalArgumentException("not sign in")
            firebaseDatabase.reference.child("messagerooms")
                .child(messageRoomId)
                .child("users")
                .updateChildren(mapOf(uid to false))
                .await()
            emit(Response.Success(Unit))
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }
}