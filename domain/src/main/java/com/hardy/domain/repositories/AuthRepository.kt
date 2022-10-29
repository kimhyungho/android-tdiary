package com.hardy.domain.repositories

import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential
import com.hardy.domain.model.Response
import com.hardy.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun isUserAuthenticated(): Flow<Boolean>

    fun oneTapSignInWithGoogle(): Flow<Response<BeginSignInResult>>

    fun firebaseSignInWithGoogle(googleCredential: AuthCredential): Flow<Response<User>>

    suspend fun signUp(): Flow<Response<User>>
}