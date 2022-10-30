package com.hardy.yongbyung.ui.home

import com.hardy.domain.model.Sport
import com.hardy.yongbyung.R
import com.hardy.yongbyung.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : BaseViewModel() {
    private val _sports: MutableStateFlow<List<Sport>> = MutableStateFlow(listOf())
    val sports: StateFlow<List<Sport>> = _sports

    init {
        _sports.value = listOf(
            Sport("전체", R.drawable.ic_all, false),
            Sport("풋살", R.drawable.ic_football, false),
            Sport("축구", R.drawable.ic_soccer, false),
            Sport("기타", R.drawable.ic_more, false)
        )
    }
}