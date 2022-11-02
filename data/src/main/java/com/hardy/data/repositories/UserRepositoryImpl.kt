package com.hardy.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.hardy.domain.model.Response
import com.hardy.domain.model.User
import com.hardy.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : UserRepository {
    override fun getUser(uid: String): Flow<Response<User.Registered>> = flow {
        try {
            emit(Response.Loading)
            val snapshot = firestore.collection("users").document(uid).get().await()
            val user = snapshot.toObject(User.Registered::class.java)
            emit(Response.Success(user))
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }
}