package com.hardy.yongbyung.ui.login

import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.hardy.domain.model.Response
import com.hardy.domain.model.User
import com.hardy.domain.repositories.AuthRepository
import com.hardy.yongbyung.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    val oneTapClient: SignInClient
) : BaseViewModel() {
    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user: StateFlow<User?> = _user

    private val _oneTapSignInResult: MutableStateFlow<BeginSignInResult?> = MutableStateFlow(null)
    val oneTapSignInResult: StateFlow<BeginSignInResult?> = _oneTapSignInResult

    fun oneTapSignIn() = viewModelScope.launch {
        authRepository.oneTapSignInWithGoogle().collect { response ->
            when (response) {
                is Response.Loading -> {

                }

                is Response.Success -> {
                    _oneTapSignInResult.value = response.data
                }

                is Response.Failure -> {

                }
            }
        }
    }

    fun signInWithGoogle(googleCredential: AuthCredential) = viewModelScope.launch {
        authRepository.firebaseSignInWithGoogle(googleCredential).collect { response ->
            when (response) {
                is Response.Loading -> {}
                is Response.Success -> {
                    _user.value = response.data
                }
                is Response.Failure -> {}
            }
        }
    }
}