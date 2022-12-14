package com.hardy.domain.repositories

import android.net.Uri
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential
import com.hardy.domain.model.Response
import com.hardy.domain.model.User
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.io.InputStream

interface AuthRepository {
    fun isUserAuthenticated(): Flow<Boolean>

    fun oneTapSignInWithGoogle(): Flow<Response<BeginSignInResult>>

    fun firebaseSignInWithGoogle(
        googleCredential: AuthCredential,
        fcmToken: String
    ): Flow<Response<User>>

    suspend fun signUp(): Flow<Response<User>>

    suspend fun logout(): Flow<Response<User>>

    suspend fun signOut(): Flow<Response<User>>
}