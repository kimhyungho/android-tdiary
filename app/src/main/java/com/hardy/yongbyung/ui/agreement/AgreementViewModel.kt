package com.hardy.yongbyung.ui.agreement

import com.hardy.yongbyung.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AgreementViewModel @Inject constructor() : BaseViewModel() {
    private val _isEssentialAgreeChecked: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isEssentialAgreeChecked: StateFlow<Boolean> = _isEssentialAgreeChecked

    fun onEssentialAgreeClick(isSelected: Boolean) {
        _isEssentialAgreeChecked.value = isSelected
    }

    fun onAllAgreeButtonClick() {
        _isEssentialAgreeChecked.value = true
    }
}