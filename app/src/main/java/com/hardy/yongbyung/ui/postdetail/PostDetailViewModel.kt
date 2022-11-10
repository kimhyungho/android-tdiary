package com.hardy.yongbyung.ui.postdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hardy.domain.model.Response
import com.hardy.domain.model.User
import com.hardy.domain.repositories.AuthRepository
import com.hardy.domain.repositories.PostRepository
import com.hardy.domain.repositories.UserRepository
import com.hardy.yongbyung.mapper.ExceptionMapper
import com.hardy.yongbyung.model.PostUiMapper
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
class PostDetailViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    val postId = savedStateHandle.get<String>("postId")

    private var _uid: String? = null
    val uid: String? get() = _uid

    private val _isMyPost: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isMyPost: StateFlow<Boolean> = _isMyPost

    private val _post: MutableStateFlow<PostUiModel?> = MutableStateFlow(null)
    val post: StateFlow<PostUiModel?> = _post

    private val _postLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val postLoading: StateFlow<Boolean> = _postLoading

    private val _userLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val userLoading: StateFlow<Boolean> = _userLoading

    private val _user: MutableStateFlow<User.Registered?> = MutableStateFlow(null)
    val user: StateFlow<User.Registered?> = _user

    private val _recruitLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val recruitLoading: StateFlow<Boolean> = _recruitLoading

    private val _error = BroadcastChannel<String>(Channel.BUFFERED)
    val error = _error.asFlow()

    init {
        postId?.let { getPost(postId) }
    }

    private fun getPost(postId: String) = viewModelScope.launch(Dispatchers.IO) {
        postRepository.getPost(postId).collect { response ->
            when (response) {
                is Response.Loading -> _postLoading.value = true
                is Response.Success -> {
                    _postLoading.value = false
                    response.data?.let { post ->
                        _post.value = PostUiMapper.mapToView(post)
                        post.second.uid?.let { ownerUid ->
                            _isMyPost.value = ownerUid == authRepository.uid
                            _uid = ownerUid
                            getUser(ownerUid)
                        }
                    }
                }
                is Response.Failure -> {
                    _postLoading.value = false
                    _error.trySend(ExceptionMapper.mapToView(response.e))
                }
            }
        }
    }

    private fun getUser(uid: String) = viewModelScope.launch(Dispatchers.IO) {
        userRepository.getUser(uid).collect { response ->
            when (response) {
                is Response.Loading -> _userLoading.value = true
                is Response.Success -> {
                    _userLoading.value = false
                    _user.value = response.data
                }
                is Response.Failure -> {
                    _userLoading.value = false
                    _error.trySend(ExceptionMapper.mapToView(response.e))
                }
            }
        }
    }

    fun finishRecruiting() = viewModelScope.launch(Dispatchers.IO) {
        postRepository.finishRecruit(postId ?: return@launch).collect { response ->
            when (response) {
                is Response.Loading -> _userLoading.value = true
                is Response.Success -> {
                    _userLoading.value = false
                    _post.value = post.value?.copy(isRecruiting = false)
                }
                is Response.Failure -> {
                    _userLoading.value = false
                    _error.trySend(ExceptionMapper.mapToView(response.e))
                }
            }
        }
    }
}