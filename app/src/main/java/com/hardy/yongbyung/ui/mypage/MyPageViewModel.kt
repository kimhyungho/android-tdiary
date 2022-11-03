package com.hardy.yongbyung.ui.mypage

import androidx.lifecycle.viewModelScope
import com.hardy.domain.model.Response
import com.hardy.domain.model.User
import com.hardy.domain.repositories.AuthRepository
import com.hardy.yongbyung.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {
    val uid = authRepository.uid

    private val _user: MutableStateFlow<User.Registered?> = MutableStateFlow(null)
    val user: StateFlow<User.Registered?> = _user

    init {
        getMe()
    }

    private fun getMe() = viewModelScope.launch(Dispatchers.IO) {
        authRepository.getMe().collect {
            when (it) {
                is Response.Loading -> {

                }

                is Response.Success -> {
                    _user.value = it.data
                }

                is Response.Failure -> {

                }
            }
        }
    }

    fun logout() = viewModelScope.launch(Dispatchers.IO) {
        authRepository.logout().collect {
            when (it) {
                is Response.Loading -> {}

                is Response.Success -> {}

                is Response.Failure -> {}
            }
        }
    }
}