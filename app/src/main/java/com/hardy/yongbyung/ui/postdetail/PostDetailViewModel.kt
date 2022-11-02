package com.hardy.yongbyung.ui.postdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hardy.domain.model.Response
import com.hardy.domain.model.User
import com.hardy.domain.repositories.PostRepository
import com.hardy.domain.repositories.UserRepository
import com.hardy.yongbyung.model.PostUiMapper
import com.hardy.yongbyung.model.PostUiModel
import com.hardy.yongbyung.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val postId = savedStateHandle.get<String>("postId")

    private var _uid: String? = null
    val uid: String? get() = _uid

    private val _post: MutableStateFlow<PostUiModel?> = MutableStateFlow(null)
    val post: StateFlow<PostUiModel?> = _post

    private val _user: MutableStateFlow<User.Registered?> = MutableStateFlow(null)
    val user: StateFlow<User.Registered?> = _user

    init {
        postId?.let { getPost(postId) }
    }

    private fun getPost(postId: String) = viewModelScope.launch(Dispatchers.IO) {
        postRepository.getPost(postId).collect {
            when (it) {
                is Response.Loading -> {

                }

                is Response.Success -> {
                    val post = it.data
                    post?.let {
                        _post.value = PostUiMapper.mapToView(post)

                        val uid = post.second.uid
                        uid?.let {
                            _uid = uid
                            getUser(uid)
                        }
                    }
                }

                is Response.Failure -> {

                }
            }
        }
    }

    private fun getUser(uid: String) = viewModelScope.launch(Dispatchers.IO) {
        userRepository.getUser(uid).collect {
            when (it) {
                is Response.Loading -> {

                }

                is Response.Success -> {
                    _user.value = it.data
                }

                is Response.Failure -> {

                }
            }
        }
    }
}