package com.hardy.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hardy.domain.model.MessageRoom
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.MessageRoomRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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

    override fun getMessageRooms(): Flow<Response<List<Pair<String?, MessageRoom?>>>> =
        callbackFlow {
            trySend(Response.Loading)
            firebaseDatabase.reference.child("messagerooms")
                .orderByChild("users/$uid").equalTo(true)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val messageRooms =
                            snapshot.children
                                .map { Pair(it.key, it.getValue(MessageRoom::class.java)) }
                        trySend(Response.Success(messageRooms))
                    }

                    override fun onCancelled(error: DatabaseError) {
                        trySend(Response.Failure(error.toException()))
                    }
                })
            awaitClose { close() }
        }
}