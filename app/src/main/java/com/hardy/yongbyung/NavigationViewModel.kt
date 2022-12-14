package com.hardy.yongbyung

import androidx.lifecycle.viewModelScope
import com.hardy.domain.repositories.AuthRepository
import com.hardy.domain.repositories.SettingRepository
import com.hardy.yongbyung.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {
    private val _isAuthenticated: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isAuthenticated: StateFlow<Boolean?> = _isAuthenticated

    init {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.isUserAuthenticated().collect { isAuthenticated ->
                _isAuthenticated.value = isAuthenticated
            }
        }
    }
}