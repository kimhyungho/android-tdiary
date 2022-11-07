package com.hardy.yongbyung.ui.mypage

import androidx.lifecycle.viewModelScope
import com.hardy.domain.model.Response
import com.hardy.domain.model.User
import com.hardy.domain.repositories.AuthRepository
import com.hardy.yongbyung.mapper.ExceptionMapper
import com.hardy.yongbyung.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
class MyPageViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {
    val uid = authRepository.uid

    private val _user: MutableStateFlow<User.Registered?> = MutableStateFlow(null)
    val user: StateFlow<User.Registered?> = _user

    private val _userLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val userLoading: StateFlow<Boolean> = _userLoading

    private val _logoutLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val logoutLoading: StateFlow<Boolean> = _logoutLoading

    private val _signOutLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val signOutLoading: StateFlow<Boolean> = _signOutLoading

    private val _error = BroadcastChannel<String>(Channel.BUFFERED)
    val error = _error.asFlow()

    init {
        getMe()
    }

    private fun getMe() = viewModelScope.launch(Dispatchers.IO) {
        authRepository.getMe().collect { response ->
            when (response) {
                is Response.Loading -> _userLoading.value = true
                is Response.Success -> {
                    _userLoading.value = false
                    _user.value = response.data
                }
                is Response.Failure -> {
                    _userLoading.value = false
                    _error.trySend(ExceptionMapper.mapToView(response.e))
                }
            }
        }
    }

    fun logout() = viewModelScope.launch(Dispatchers.IO) {
        authRepository.logout().collect { response ->
            when (response) {
                is Response.Loading -> _logoutLoading.value = true
                is Response.Success -> _logoutLoading.value = false
                is Response.Failure -> {
                    _logoutLoading.value = false
                    _error.trySend(ExceptionMapper.mapToView(response.e))
                }
            }
        }
    }

    fun signOut() = viewModelScope.launch(Dispatchers.IO) {
        authRepository.signOut().collect { response ->
            when (response) {
                is Response.Loading -> _signOutLoading.value = true
                is Response.Success -> _signOutLoading.value = false
                is Response.Failure -> {
                    _signOutLoading.value = false
                    _error.trySend(ExceptionMapper.mapToView(response.e))
                }
            }
        }
    }
}