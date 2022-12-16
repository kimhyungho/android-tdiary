package com.hardy.yongbyung.ui.postdetail

import com.hardy.domain.model.Post
import com.hardy.yongbyung.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(): BaseViewModel() {
    private val _post: MutableStateFlow<Post?> = MutableStateFlow(null)
    private val post: StateFlow<Post?> = _post
}