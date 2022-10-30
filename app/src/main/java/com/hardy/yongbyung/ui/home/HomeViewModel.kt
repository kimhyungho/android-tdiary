package com.hardy.yongbyung.ui.home

import com.hardy.yongbyung.R
import com.hardy.yongbyung.model.CategoryUiModel
import com.hardy.yongbyung.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : BaseViewModel() {
    private val _categories: MutableStateFlow<List<CategoryUiModel>> = MutableStateFlow(listOf())
    val categories: StateFlow<List<CategoryUiModel>> = _categories

    init {
        _categories.value = listOf(
            CategoryUiModel("전체", R.drawable.ic_all, false),
            CategoryUiModel("풋살", R.drawable.ic_football, false),
            CategoryUiModel("축구", R.drawable.ic_soccer, false),
            CategoryUiModel("기타", R.drawable.ic_more, false)
        )
    }
}