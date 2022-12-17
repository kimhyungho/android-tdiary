package com.hardy.yongbyung.ui.postdetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hardy.domain.interactors.posts.DeletePostUseCase
import com.hardy.domain.model.Post
import com.hardy.domain.model.Response
import com.hardy.yongbyung.mapper.ExceptionMapper
import com.hardy.yongbyung.mapper.PostUiMapper
import com.hardy.yongbyung.model.PostUiModel
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
class PostDetailViewModel @Inject constructor(
    private val deletePostUseCase: DeletePostUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val _post: MutableStateFlow<PostUiModel?> = MutableStateFlow(null)
    val post: StateFlow<PostUiModel?> = _post

    private val _deleteLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val deleteLoading: StateFlow<Boolean> = _deleteLoading

    private val _showBack: MutableStateFlow<Unit?> = MutableStateFlow(null)
    val showBack: StateFlow<Unit?> = _showBack

    private val _error = BroadcastChannel<String>(Channel.BUFFERED)
    val error = _error.asFlow()

    init {
        savedStateHandle.get<Post>(POST_KEY)?.let { post ->
            _post.value = PostUiMapper.mapToView(post)
        }
    }

    fun delete() = viewModelScope.launch(Dispatchers.IO) {
        val id = post.value?.id ?: return@launch
        deletePostUseCase(id).collect { response ->
            when (response) {
                is Response.Loading -> _deleteLoading.value = true
                is Response.Success -> {
                    _deleteLoading.value = false
                    _showBack.value = Unit
                }
                is Response.Failure -> {
                    _deleteLoading.value = false
                    _error.trySend(ExceptionMapper.mapToView(response.e))
                }
            }
        }
    }

    companion object {
        const val POST_KEY = "post"
    }
}