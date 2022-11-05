package com.hardy.yongbyung.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.hardy.domain.repositories.PostRepository
import com.hardy.yongbyung.R
import com.hardy.yongbyung.mapper.ExceptionMapper
import com.hardy.yongbyung.model.CategoryUiModel
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
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository
) : BaseViewModel() {
    private val _categories: MutableStateFlow<List<CategoryUiModel>> = MutableStateFlow(listOf())
    val categories: StateFlow<List<CategoryUiModel>> = _categories

    private val _posts: MutableStateFlow<PagingData<PostUiModel>> =
        MutableStateFlow(PagingData.empty())
    val posts: StateFlow<PagingData<PostUiModel>> = _posts

    private val _selectedMainRegion: MutableStateFlow<String> = MutableStateFlow("전국")
    val selectedMainRegion: StateFlow<String> = _selectedMainRegion

    private val _selectedSubRegion: MutableStateFlow<String> = MutableStateFlow("전체")
    val selectedSubRegion: StateFlow<String> = _selectedSubRegion

    private val _refreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val refreshing: StateFlow<Boolean> = _refreshing

    private val _postLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val postLoading: StateFlow<Boolean> = _postLoading

    private val _error = BroadcastChannel<String>(Channel.BUFFERED)
    val error = _error.asFlow()

    init {
        _categories.value = listOf(
            CategoryUiModel("전체", R.drawable.ic_all, true),
            CategoryUiModel("풋살", R.drawable.ic_football, false),
            CategoryUiModel("축구", R.drawable.ic_soccer, false),
            CategoryUiModel("기타", R.drawable.ic_more, false)
        )
        getPosts()
    }

    private fun getPosts() = viewModelScope.launch(Dispatchers.IO) {
        val category = categories.value.first { it.isSelected }.name
        val mainRegion = selectedMainRegion.value
        val subRegion = selectedSubRegion.value

        postRepository.getPosts(category, mainRegion, subRegion)
            .cachedIn(viewModelScope)
            .collect {
                _posts.value = it.map(PostUiMapper::mapToView)
            }
    }

    fun onCategorySelect(category: String) {
        _categories.value = categories.value.map {
            if (it.name == category) it.copy(isSelected = true)
            else it.copy(isSelected = false)
        }
        getPosts()
    }

    fun onFilterSelected(selectedMainRegion: String, selectedSubRegion: String) {
        _selectedMainRegion.value = selectedMainRegion
        _selectedSubRegion.value = selectedSubRegion
        getPosts()
    }

    fun setRefreshing(isRefreshing: Boolean) {
        _refreshing.value = isRefreshing
    }

    fun setPostLoading(isPostLoading: Boolean) {
        _postLoading.value = isPostLoading
    }

    fun setError(throwable: Throwable) {
        _error.trySend(ExceptionMapper.mapToView(Exception(throwable)))
    }
}