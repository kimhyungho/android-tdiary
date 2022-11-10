package com.hardy.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.snapshots
import com.hardy.domain.model.Message
import com.hardy.domain.model.MessageRoom
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.MessageRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
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

    override suspend fun sendMessage(receiverUid: String, message: String): Flow<Response<String>> =
        flow {
            try {
                emit(Response.Loading)
                uid ?: throw IllegalArgumentException("not sign in")

                val messageRoomsSnapshot = firebaseDatabase.reference.child("messagerooms")
                    .orderByChild("users/$uid")
                    .equalTo(true).snapshots.first()

                var keyAndMessageRoom: Pair<String, MessageRoom>? = null

                messageRoomsSnapshot.children.forEach { snapshot ->
                    val messageRoom = snapshot.getValue(MessageRoom::class.java)
                    if (messageRoom?.users?.containsKey(receiverUid) == true) {
                        keyAndMessageRoom = Pair(snapshot.key!!, messageRoom)
                    }
                }

                val messageModel = Message(
                    uid = uid,
                    message = message,
                    createdAt = Date(),
                    readUsers = mapOf(uid!! to true)
                )

                if (keyAndMessageRoom != null) {
                    firebaseDatabase.reference.child("messagerooms")
                        .child(keyAndMessageRoom!!.first)
                        .child("messages")
                        .push()
                        .setValue(messageModel)
                        .await()
                    emit(Response.Success(keyAndMessageRoom!!.first))
                } else {
                    val randomUid = firebaseDatabase.reference.push().key!!
                    val messageRoom = MessageRoom(
                        users = mapOf(uid!! to true, receiverUid to true),
                        messages = mapOf(randomUid to messageModel)
                    )
                    firebaseDatabase.reference.child("messagerooms")
                        .push()
                        .setValue(messageRoom)
                        .await()
                    emit(Response.Success(randomUid))
                }
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }
        }

    override fun getMessages(messageRoomId: String): Flow<Response<List<Pair<String?, Message?>>>> =
        callbackFlow {
            trySend(Response.Loading)
            firebaseDatabase.reference.child("messagerooms")
                .child(messageRoomId)
                .child("messages")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val messages =
                            snapshot.children.map { Pair(it.key, it.getValue(Message::class.java)) }
                        trySend(Response.Success(messages))
                    }

                    override fun onCancelled(error: DatabaseError) {
                        trySend(Response.Failure(error.toException()))
                    }
                })
            awaitClose { close() }
        }
}