package com.hardy.yongbyung.ui.home

import androidx.lifecycle.viewModelScope
import com.hardy.domain.interactors.posts.GetPostsByUidUseCase
import com.hardy.domain.model.Response
import com.hardy.yongbyung.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
//    private val getPostsByUidUseCase: GetPostsByUidUseCase
) : BaseViewModel() {
//    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            getPostsByUidUseCase().collect { response ->
//                when (response) {
//                    is Response.Loading -> {}
//                    is Response.Success -> {
//
//                    }
//                    is Response.Failure -> {}
//                }
//            }
//        }
//    }
}