package com.hardy.data.repositories

import androidx.datastore.core.DataStore

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.hardy.data.Constants.SIGN_IN_REQUEST
import com.hardy.data.Constants.SIGN_UP_REQUEST
import com.hardy.data.di.qualifiers.UsersQualifier
import com.hardy.data.mapper.UserMapper
import com.hardy.domain.exceptions.YongByungException
import com.hardy.domain.model.Response
import com.hardy.domain.model.User
import com.hardy.domain.repositories.AuthRepository
import com.hardy.yongbyung.datastore.UserPreferences
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private var oneTapClient: SignInClient,
    @Named(SIGN_IN_REQUEST) private var signInRequest: BeginSignInRequest,
    @Named(SIGN_UP_REQUEST) private var signUpRequest: BeginSignInRequest,
    @UsersQualifier
    private val userRef: CollectionReference,
    private val userDataStore: DataStore<UserPreferences>,
) : AuthRepository {
    val uid get() = auth.currentUser?.uid

    override fun isUserAuthenticated(): Flow<Boolean> = userDataStore.data.map { userPref ->
        !userPref.nickname.isNullOrEmpty() && auth.currentUser != null
    }

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
                emit(Response.Failure(YongByungException.parseFrom(e)))
            }
        }
    }

    override fun firebaseSignInWithGoogle(googleCredential: AuthCredential, fcmToken: String) =
        flow {
            try {
                emit(Response.Loading)
                val firebaseAuth = auth.signInWithCredential(googleCredential).await()
                val uid = firebaseAuth.user?.uid ?: throw IllegalArgumentException("not sign in")
                val snapshot = userRef.document(uid).get().await()
                if (snapshot.exists()) {
                    val user = snapshot.toObject(User.Registered::class.java)
                    user?.let { emit(Response.Success(saveUser(user))) }
                } else {
                    emit(Response.Success(User.UnRegistered))
                }
            } catch (e: Exception) {
                emit(Response.Failure(YongByungException.parseFrom(e)))
            }
        }

    override suspend fun signUp(): Flow<Response<User>> = flow {
        try {
            emit(Response.Loading)
            val currentUser = auth.currentUser ?: throw IllegalArgumentException("not sign in")
            val uid = currentUser.uid
            val nickname = currentUser.displayName ?: "MR.T"
            val user = User.Registered(
                nickname = nickname,
                profileImage = INIT_PROFILE_IMAGE,
                createdAt = Date()
            )
            userRef.document(uid).set(user).await()
            emit(Response.Success(saveUser(user)))
        } catch (e: Exception) {
            emit(Response.Failure(YongByungException.parseFrom(e)))
        }
    }

    override suspend fun logout(): Flow<Response<User>> = flow {
        try {
            emit(Response.Loading)
            auth.signOut()
            emit(Response.Success(UserMapper.mapToDomain(userDataStore.updateData { pref ->
                pref.toBuilder()
                    .clear()
                    .build()
            })))
        } catch (e: Exception) {
            emit(Response.Failure(YongByungException.parseFrom(e)))
        }
    }

    override suspend fun signOut(): Flow<Response<User>> = flow {
        try {
            emit(Response.Loading)
            emit(Response.Success(UserMapper.mapToDomain(userDataStore.updateData { pref ->
                auth.currentUser?.delete()
                pref.toBuilder()
                    .clear()
                    .build()
            })))
        } catch (e: Exception) {
            emit(Response.Failure(YongByungException.parseFrom(e)))
        }
    }

    private suspend fun saveUser(user: User.Registered): User.Registered {
        return UserMapper.mapToDomain(userDataStore.updateData { pref ->
            pref.toBuilder()
                .setNickname(user.nickname)
                .setProfileImage(user.profileImage)
                .setCreatedAt(
                    user.createdAt?.time
                        ?: throw IllegalArgumentException("createdAt can not be null")
                ).build()
        })
    }

    companion object {
        const val INIT_PROFILE_IMAGE =
            "https://firebasestorage.googleapis.com/v0/b/yongbyung-b2aa8.appspot.com/o/images%2Fdefault_profile.png?alt=media&token=660e2d25-4834-4a35-8309-bf94e9d9cab9"
    }
}