package com.hardy.data.repositories

import android.util.Log
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hardy.data.Constants.SIGN_IN_REQUEST
import com.hardy.data.Constants.SIGN_UP_REQUEST
import com.hardy.domain.model.Response
import com.hardy.domain.model.User
import com.hardy.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private var oneTapClient: SignInClient,
    @Named(SIGN_IN_REQUEST) private var signInRequest: BeginSignInRequest,
    @Named(SIGN_UP_REQUEST) private var signUpRequest: BeginSignInRequest,
    private val firestore: FirebaseFirestore
) : AuthRepository {
    override val isUserAuthenticatedInFirebase = auth.currentUser != null

    override fun oneTapSignInWithGoogle() = flow {
        try {
            emit(Response.Loading)
            val result = oneTapClient.beginSignIn(signInRequest).await()
            emit(Response.Success(result))
        } catch (e: Exception) {
            try {
                val signUpResult = oneTapClient.beginSignIn(signUpRequest).await()
                emit(Response.Success(signUpResult))
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }
        }
    }

    override fun firebaseSignInWithGoogle(googleCredential: AuthCredential) = flow {
        try {
            emit(Response.Loading)
            val firebaseAuth = auth.signInWithCredential(googleCredential).await()
            val uid = firebaseAuth.user?.uid ?: throw IllegalArgumentException("not sign in")
            val snapshot = firestore.collection("users").document(uid).get().await()
            if (snapshot.exists()) {
                val user = snapshot.toObject(User.Registered::class.java)
                emit(Response.Success(user))
            } else {
                emit(Response.Success(User.UnRegistered))
            }
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }

//    private suspend fun addUserToFirestore() {
//        auth.currentUser?.apply {
//            val user = toUser()
//            firestore.collection(USERS).document(uid).set(user).await()
//        }
//    }

//    private fun FirebaseUser.toUser() = mapOf(
//        DISPLAY_NAME to displayName,
//        EMAIL to email,
//        PHOTO_URL to photoUrl?.toString(),
//        CREATED_AT to FieldValue.serverTimestamp()
//    )
}