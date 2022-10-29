package com.hardy.yongbyung.ui.agreement

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.hardy.domain.repositories.AuthRepository
import com.hardy.yongbyung.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgreementViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {
    private val _isEssentialAgreeChecked: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isEssentialAgreeChecked: StateFlow<Boolean> = _isEssentialAgreeChecked

    fun onEssentialAgreeClick(isSelected: Boolean) {
        _isEssentialAgreeChecked.value = isSelected
    }

    fun onAllAgreeButtonClick() {
        _isEssentialAgreeChecked.value = true
    }

    fun onNextButtonClick() = viewModelScope.launch(Dispatchers.IO) {
        authRepository.signUp().collect {
            Log.d("kkkk", it.toString())
        }
    }
}