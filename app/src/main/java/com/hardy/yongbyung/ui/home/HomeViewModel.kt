package com.hardy.yongbyung.ui.home

import androidx.lifecycle.viewModelScope
import com.hardy.domain.interactors.posts.GetPostsUseCase
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ObsoleteCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPostsByUidUseCase: GetPostsUseCase
) : BaseViewModel() {
    private val _posts: MutableStateFlow<List<PostUiModel>> = MutableStateFlow(listOf())
    val posts: StateFlow<List<PostUiModel>> = _posts

    private val _getPostsLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val getPostsLoading: StateFlow<Boolean> = _getPostsLoading

    private val _error = BroadcastChannel<String>(Channel.BUFFERED)
    val error = _error.asFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getPostsByUidUseCase().collect { response ->
                when (response) {
                    is Response.Loading -> _getPostsLoading.value = true
                    is Response.Success -> {
                        _getPostsLoading.value = false
                        val posts = response.data ?: listOf()
                        _posts.value = posts.map(PostUiMapper::mapToView)
                    }
                    is Response.Failure -> {
                        _getPostsLoading.value = false
                        _error.trySend(ExceptionMapper.mapToView(response.e))
                    }
                }
            }
        }
    }
}