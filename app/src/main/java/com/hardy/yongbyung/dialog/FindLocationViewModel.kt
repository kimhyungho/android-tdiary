package com.hardy.yongbyung.dialog

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hardy.domain.interactors.kakao.GetPlaceByQueryUseCase
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.KakaoMapRepository
import com.hardy.yongbyung.mapper.ExceptionMapper
import com.hardy.yongbyung.mapper.PlaceUiMapper
import com.hardy.yongbyung.model.PlaceUiModel
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
class FindLocationViewModel @Inject constructor(
    private val getPlaceByQueryUseCase: GetPlaceByQueryUseCase
) : BaseViewModel() {
    private val _places: MutableStateFlow<List<PlaceUiModel>> = MutableStateFlow(listOf())
    val places: StateFlow<List<PlaceUiModel>> = _places

    private val _isConfirmButtonEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isConfirmButtonEnabled: StateFlow<Boolean> = _isConfirmButtonEnabled

    private val _error = BroadcastChannel<String>(Channel.BUFFERED)
    val error = _error.asFlow()


    fun onLocationNameTextChange(location: CharSequence) = viewModelScope.launch(Dispatchers.IO) {
        val locationName = location.toString()
        getPlaceByQueryUseCase(locationName).collect { response ->
            when (response) {
                is Response.Loading -> {}
                is Response.Success -> {
                    _places.value = response.data?.map(PlaceUiMapper::mapToView) ?: listOf()
                }
                is Response.Failure -> {
                    _error.trySend(ExceptionMapper.mapToView(response.e))
                }
            }
        }
    }

    fun onPlaceSelect(place: PlaceUiModel) {
        _places.value = places.value.map {
            if (place.id == it.id) it.copy(isSelected = !it.isSelected)
            else it.copy(isSelected = false)
        }
        _isConfirmButtonEnabled.value = !places.value.none { it.isSelected }
    }
}