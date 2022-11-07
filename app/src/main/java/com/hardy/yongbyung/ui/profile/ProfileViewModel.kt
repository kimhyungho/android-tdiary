package com.hardy.yongbyung.ui.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.hardy.domain.model.Response
import com.hardy.domain.model.User
import com.hardy.domain.repositories.PostRepository
import com.hardy.domain.repositories.UserRepository
import com.hardy.yongbyung.mapper.ExceptionMapper
import com.hardy.yongbyung.model.PostUiMapper
import com.hardy.yongbyung.model.PostUiModel
import com.hardy.yongbyung.model.UserUiMapper
import com.hardy.yongbyung.model.UserUiModel
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
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val _posts: MutableStateFlow<PagingData<PostUiModel>> =
        MutableStateFlow(PagingData.empty())
    val posts: StateFlow<PagingData<PostUiModel>> = _posts

    private val _showEmptyImage: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showEmptyImage: StateFlow<Boolean> = _showEmptyImage

    private val _error = BroadcastChannel<String>(Channel.BUFFERED)
    val error = _error.asFlow()

    private val _postLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val postLoading: StateFlow<Boolean> = _postLoading

    private val _user: MutableStateFlow<UserUiModel?> = MutableStateFlow(null)
    val user: StateFlow<UserUiModel?> = _user

    init {
        val uid = savedStateHandle.get<String>("uid")
        uid?.let {
            getUser(uid)
            getPosts(uid)
        }
    }

    private fun getUser(uid: String) = viewModelScope.launch(Dispatchers.IO) {
        userRepository.getUser(uid).collect { response ->
            when (response) {
                is Response.Loading -> _postLoading.value = true
                is Response.Success -> {
                    _postLoading.value = false
                    val user = response.data
                    user?.let { _user.value = UserUiMapper.mapToView(user) }
                }
                is Response.Failure -> {
                    _postLoading.value = false
                    _error.trySend(ExceptionMapper.mapToView(response.e))
                }
            }
        }
    }

    private fun getPosts(uid: String) = viewModelScope.launch(Dispatchers.IO) {
        postRepository.getPosts(uid)
            .cachedIn(viewModelScope)
            .collect {
                _posts.value = it.map(PostUiMapper::mapToView)
            }
    }

    fun setError(throwable: Throwable) {
        _error.trySend(ExceptionMapper.mapToView(Exception(throwable)))
    }

    fun setShowEmptyImage(isShow: Boolean) {
        _showEmptyImage.value = isShow
    }

    fun setPostLoading(isLoading: Boolean) {
        _postLoading.value = isLoading
    }
}