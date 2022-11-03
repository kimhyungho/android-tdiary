package com.hardy.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hardy.domain.model.Message
import com.hardy.domain.model.MessageRoom
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.MessageRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : MessageRepository {
    private val uid get() = firebaseAuth.currentUser?.uid

    override suspend fun sendMessage(receiverUid: String, message: String) {
        try {
            uid ?: throw IllegalArgumentException("not sign in")

            val chatroom = firebaseDatabase.reference.child("messagerooms")
                .orderByChild("users/$uid").equalTo(true)
                .orderByChild("users/$receiverUid").equalTo(true)
                .get().await()

            val messageModel = Message(
                uid = uid,
                message = message,
                createdAt = Date(),
                readUsers = mapOf(uid!! to true)
            )

            if (chatroom.exists()) {
                firebaseDatabase.reference.child("messagerooms").child(chatroom.key!!)
                    .child("messages")
                    .push()
                    .setValue(messageModel)
            } else {
                val messageRoom = MessageRoom(users = mapOf(uid!! to true, receiverUid to true))
                firebaseDatabase.reference.child("messagerooms").push().setValue(messageRoom)
                    .await()
            }
        } catch (e: Exception) {

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