package com.hardy.yongbyung.ui.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.hardy.domain.model.Response
import com.hardy.domain.model.User
import com.hardy.domain.repositories.AuthRepository
import com.hardy.yongbyung.mapper.ExceptionMapper
import com.hardy.yongbyung.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ObsoleteCoroutinesApi::class)
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    val oneTapClient: SignInClient
) : BaseViewModel() {
    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user: StateFlow<User?> = _user

    private val _oneTapSignInResult: MutableStateFlow<BeginSignInResult?> = MutableStateFlow(null)
    val oneTapSignInResult: StateFlow<BeginSignInResult?> = _oneTapSignInResult

    private val _loginLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loginLoading: StateFlow<Boolean> = _loginLoading

    private val _error = BroadcastChannel<String>(Channel.BUFFERED)
    val error = _error.asFlow()


    fun oneTapSignIn() = viewModelScope.launch {
        authRepository.oneTapSignInWithGoogle().collect { response ->
            when (response) {
                is Response.Loading -> _loginLoading.value = true
                is Response.Success -> {
                    _loginLoading.value = false
                    _oneTapSignInResult.value = response.data
                }
                is Response.Failure -> {
                    _loginLoading.value = false
                    _error.trySend(ExceptionMapper.mapToView(response.e))
                }
            }
        }
    }

    fun signInWithGoogle(googleCredential: AuthCredential) = viewModelScope.launch {
        authRepository.firebaseSignInWithGoogle(googleCredential).collect { response ->
            when (response) {
                is Response.Loading -> _loginLoading.value = true
                is Response.Success -> {
                    _loginLoading.value = false
                    _user.value = response.data
                }
                is Response.Failure -> {
                    _loginLoading.value = false
                    _error.trySend(ExceptionMapper.mapToView(response.e))
                }
            }
        }
    }
}