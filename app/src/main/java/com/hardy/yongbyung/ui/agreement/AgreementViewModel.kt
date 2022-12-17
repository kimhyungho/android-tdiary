package com.hardy.yongbyung.ui.agreement

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.hardy.domain.interactors.auth.SignUpUseCase
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ObsoleteCoroutinesApi::class)
@HiltViewModel
class AgreementViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : BaseViewModel() {
    private val _isEssentialAgreeChecked: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isEssentialAgreeChecked: StateFlow<Boolean> = _isEssentialAgreeChecked

    private val _signUpLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val signUpLoading: StateFlow<Boolean> = _signUpLoading

    private val _isEssentialExpanded: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isEssentialExpanded: StateFlow<Boolean> = _isEssentialExpanded

    private val _error = BroadcastChannel<String>(Channel.BUFFERED)
    val error = _error.asFlow()

    fun onEssentialAgreeClick(isSelected: Boolean) {
        _isEssentialAgreeChecked.value = isSelected
    }

    fun onAllAgreeButtonClick() {
        _isEssentialAgreeChecked.value = true
    }

    fun onNextButtonClick() = viewModelScope.launch(Dispatchers.IO) {
        signUpUseCase().collect { response ->
            when (response) {
                is Response.Loading -> _signUpLoading.value = true
                is Response.Success -> _signUpLoading.value = false
                is Response.Failure -> {
                    Log.d("kkkk", "", response.e)
                    _signUpLoading.value = false
                    _error.trySend(ExceptionMapper.mapToView(response.e))
                }
            }
        }
    }

    fun onEssentialIconClick() {
        _isEssentialExpanded.value = !isEssentialExpanded.value
    }
}