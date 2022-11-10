package com.hardy.yongbyung.dialog

import androidx.lifecycle.viewModelScope
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.AuthRepository
import com.hardy.domain.repositories.PostRepository
import com.hardy.domain.repositories.ReportRepository
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ObsoleteCoroutinesApi::class)
@HiltViewModel
class PostMenuViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val postRepository: PostRepository,
    private val reportRepository: ReportRepository
) : BaseViewModel() {
    private val _isMyPost: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isMyPost: StateFlow<Boolean> = _isMyPost

    private val _successDelete: MutableStateFlow<Unit?> = MutableStateFlow(null)
    val successDelete: StateFlow<Unit?> = _successDelete

    private val _reportLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val reportLoading: StateFlow<Boolean> = _reportLoading

    private val _deleteLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val deleteLoading: StateFlow<Boolean> = _deleteLoading

    private val _error = BroadcastChannel<String>(Channel.BUFFERED)
    val error = _error.asFlow()

    fun deletePost(postId: String) = viewModelScope.launch(Dispatchers.IO) {
        postRepository.deletePost(postId).collect { response ->
            when (response) {
                is Response.Loading -> _deleteLoading.value = true
                is Response.Success -> {
                    _deleteLoading.value = false
                    _successDelete.value = Unit
                }
                is Response.Failure -> {
                    _deleteLoading.value = false
                    _error.trySend(ExceptionMapper.mapToView(response.e))
                }
            }
        }
    }

    fun setIsMyPost(uid: String) {
        _isMyPost.value = authRepository.uid == uid
    }
}