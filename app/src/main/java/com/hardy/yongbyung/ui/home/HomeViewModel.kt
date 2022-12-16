package com.hardy.yongbyung.ui.home

import androidx.lifecycle.viewModelScope
import com.hardy.domain.interactors.posts.GetPostsUseCase
import com.hardy.domain.model.Post
import com.hardy.domain.model.Response
import com.hardy.yongbyung.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPostsByUidUseCase: GetPostsUseCase
) : BaseViewModel() {
    private val _posts: MutableStateFlow<List<Post>> = MutableStateFlow(listOf())
    val posts: StateFlow<List<Post>> = _posts

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getPostsByUidUseCase().collect { response ->
                when (response) {
                    is Response.Loading -> {}
                    is Response.Success -> {
                        val posts = response.data ?: listOf()
                        _posts.value = posts
                    }
                    is Response.Failure -> {}
                }
            }
        }
    }
}