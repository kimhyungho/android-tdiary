package com.hardy.yongbyung.dialog

import com.hardy.yongbyung.R
import com.hardy.yongbyung.model.MainRegionUiModel
import com.hardy.yongbyung.model.SubRegionUiModel
import com.hardy.yongbyung.provider.ResourceProvider
import com.hardy.yongbyung.ui.base.BaseViewModel
import com.hardy.yongbyung.utils.CsvHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SelectSidoViewModel @Inject constructor(
    resourceProvider: ResourceProvider
) : BaseViewModel() {
    private val regions = CsvHelper(resourceProvider.getRawInputStream(R.raw.sido_data))
        .readData().map { Pair(it.first().toString(), it.last()) }
        .filterIndexed { index, _ -> index != 0 }
        .groupBy { it.first }

    private val _mainRegions: MutableStateFlow<List<MainRegionUiModel>> = MutableStateFlow(listOf())
    val mainRegions: StateFlow<List<MainRegionUiModel>> = _mainRegions

    private val _subRegions: MutableStateFlow<List<SubRegionUiModel>> = MutableStateFlow(listOf())
    val subRegions: StateFlow<List<SubRegionUiModel>> = _subRegions

    private val _isConfirmEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isConfirmEnabled: StateFlow<Boolean> = _isConfirmEnabled

    fun setRegionFilter(mainRegion: String?, subRegion: String?) {
        _mainRegions.value = regions.keys.map { MainRegionUiModel(it, false) }
        onMainRegionSelect(mainRegion ?: regions.keys.first())
        subRegion?.let { onSubRegionSelect(subRegion) }
    }

    fun onMainRegionSelect(name: String?) {
        _mainRegions.value = mainRegions.value.map {
            if (it.name == name) {
                it.copy(isSelected = true)
            } else {
                it.copy(isSelected = false)
            }
        }
        name?.let {
            _subRegions.value =
                regions[name]?.map { SubRegionUiModel(it.second, false) } ?: listOf()
        }
        _isConfirmEnabled.value = false
    }

    fun onSubRegionSelect(name: String) {
        _subRegions.value = subRegions.value.map {
            if (it.name == name) it.copy(isSelected = true)
            else it.copy(isSelected = false)
        }
        _isConfirmEnabled.value = true
    }
}