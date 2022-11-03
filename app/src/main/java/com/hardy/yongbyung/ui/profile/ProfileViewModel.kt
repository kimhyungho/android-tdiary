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
import com.hardy.yongbyung.model.PostUiMapper
import com.hardy.yongbyung.model.PostUiModel
import com.hardy.yongbyung.model.UserUiMapper
import com.hardy.yongbyung.model.UserUiModel
import com.hardy.yongbyung.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val _posts: MutableStateFlow<PagingData<PostUiModel>> =
        MutableStateFlow(PagingData.empty())
    val posts: StateFlow<PagingData<PostUiModel>> = _posts

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
        userRepository.getUser(uid).collect {
            when (it) {
                is Response.Loading -> {

                }

                is Response.Success -> {
                    val user = it.data
                    user?.let { _user.value = UserUiMapper.mapToView(user) }
                }

                is Response.Failure -> {

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
}