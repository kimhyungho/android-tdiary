package com.hardy.yongbyung.ui.intro

import android.Manifest
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.hardy.domain.repositories.SettingRepository
import com.hardy.yongbyung.provider.permission.PermissionProvider
import com.hardy.yongbyung.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val permissionProvider: PermissionProvider,
    private val settingRepository: SettingRepository
) : BaseViewModel() {
    private val _showLoginPage: MutableStateFlow<Unit?> = MutableStateFlow(null)
    val showLoginPage: StateFlow<Unit?> = _showLoginPage

    init {
        viewModelScope.launch(Dispatchers.IO) {
            settingRepository.getIsPermissionChecked().collect {
                if (it) _showLoginPage.value = Unit
            }
        }
    }

    fun onStartButtonClick() = viewModelScope.launch(Dispatchers.IO) {
        permissionProvider.checkPermissions(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.CAMERA
        ).collect { settingRepository.setIsPermissionChecked(true) }
    }
}