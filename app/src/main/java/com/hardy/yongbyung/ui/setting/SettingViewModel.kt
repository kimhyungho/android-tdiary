package com.hardy.yongbyung.ui.setting

import androidx.lifecycle.viewModelScope
import com.hardy.domain.interactors.auth.LogoutUseCase
import com.hardy.domain.interactors.auth.SignOutUseCase
import com.hardy.domain.model.Response
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ObsoleteCoroutinesApi::class)
@HiltViewModel
class SettingViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val signOutUseCase: SignOutUseCase
) : BaseViewModel() {
    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = BroadcastChannel<String>(Channel.BUFFERED)
    val error = _error.asFlow()

    fun logout() = viewModelScope.launch(Dispatchers.IO) {
        logoutUseCase().collect { response ->
            when (response) {
                is Response.Loading -> _isLoading.value = true
                is Response.Success -> _isLoading.value = false
                is Response.Failure -> {
                    _isLoading.value = false
                    _error.trySend(ExceptionMapper.mapToView(response.e))
                }
            }
        }
    }

    fun signOut() = viewModelScope.launch(Dispatchers.IO) {
        signOutUseCase().collect { response ->
            when (response) {
                is Response.Loading -> _isLoading.value = true
                is Response.Success -> _isLoading.value = false
                is Response.Failure -> {
                    _isLoading.value = false
                    _error.trySend(ExceptionMapper.mapToView(response.e))
                }
            }
        }
    }
}