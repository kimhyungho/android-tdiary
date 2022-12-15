package com.hardy.yongbyung.dialog

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.KakaoMapRepository
import com.hardy.yongbyung.mapper.PlaceUiMapper
import com.hardy.yongbyung.model.PlaceUiModel
import com.hardy.yongbyung.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindLocationViewModel @Inject constructor(
    private val kakaoMapRepository: KakaoMapRepository
) : BaseViewModel() {
    private val _places: MutableStateFlow<List<PlaceUiModel>> = MutableStateFlow(listOf())
    val places: StateFlow<List<PlaceUiModel>> = _places

    private val _isConfirmButtonEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isConfirmButtonEnabled: StateFlow<Boolean> = _isConfirmButtonEnabled

    fun onLocationNameTextChange(location: CharSequence) = viewModelScope.launch(Dispatchers.IO) {
        val locationName = location.toString()
        kakaoMapRepository.getPlaceByQuery(locationName).collect { response ->
            when (response) {
                is Response.Success -> {
                    _places.value = response.data?.map(PlaceUiMapper::mapToView) ?: listOf()
                }

                is Response.Loading -> {

                }

                is Response.Failure -> {

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