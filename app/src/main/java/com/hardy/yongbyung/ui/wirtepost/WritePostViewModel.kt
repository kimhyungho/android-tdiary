package com.hardy.yongbyung.ui.wirtepost

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.PostRepository
import com.hardy.yongbyung.mapper.ExceptionMapper
import com.hardy.yongbyung.model.CategoryUiModel
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
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@OptIn(ObsoleteCoroutinesApi::class)
@HiltViewModel
class WritePostViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    val category: StateFlow<CategoryUiModel?> = savedStateHandle.getStateFlow(CATEGORY_KEY, null)
    val title: StateFlow<String> = savedStateHandle.getStateFlow(TITLE_KEY, "")
    val content: StateFlow<String> = savedStateHandle.getStateFlow(CONTENT_KEY, "")
    val location: StateFlow<String> = savedStateHandle.getStateFlow(LOCATION_KEY, "")
    val date: StateFlow<Pair<String, Date>?> = savedStateHandle.getStateFlow(DATE_KEY, null)
    val selectedMainRegion: StateFlow<String?> =
        savedStateHandle.getStateFlow(MAIN_REGION_KEY, null)
    val selectedSubRegion: StateFlow<String?> = savedStateHandle.getStateFlow(SUB_REGION_KEY, null)

    private val _writeLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val writeLoading: StateFlow<Boolean> = _writeLoading

    private val _showPostDetail: MutableStateFlow<String?> = MutableStateFlow(null)
    val showPostDetail: StateFlow<String?> = _showPostDetail

    private val _error = BroadcastChannel<String>(Channel.BUFFERED)
    val error = _error.asFlow()

    fun setSelectedCategory(categoryUiModel: CategoryUiModel) {
        savedStateHandle[CATEGORY_KEY] = categoryUiModel
    }

    fun onTitleTextChanged(title: CharSequence) {
        savedStateHandle[TITLE_KEY] = title.toString()
    }

    fun onContentTextChanged(content: CharSequence) {
        savedStateHandle[CONTENT_KEY] = content.toString()
    }

    fun onLocationTextChanged(location: CharSequence) {
        savedStateHandle[LOCATION_KEY] = location.toString()
    }

    @SuppressLint("SimpleDateFormat")
    fun onDateSelected(selectedDate: Long) {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd(E)")
        val stringDate = dateFormat.format(selectedDate)
        val date = Date(selectedDate)
        savedStateHandle[DATE_KEY] = Pair<String, Date>(stringDate, date)
    }

    fun onRegionSelected(selectedMainRegion: String, selectedSubRegion: String) {
        savedStateHandle[MAIN_REGION_KEY] = selectedMainRegion
        savedStateHandle[SUB_REGION_KEY] = selectedSubRegion
    }

    fun onWriteButtonClick() {
        val category = this.category.value?.name ?: run {
            _error.trySend("카테고리는 필수입니다.")
            return
        }
        val date = this.date.value?.second ?: run {
            _error.trySend("날짜는 필수입니다.")
            return
        }
        val mainRegion = this.selectedMainRegion.value ?: run {
            _error.trySend("지역은 필수입니다.")
            return
        }
        val subRegion = this.selectedSubRegion.value ?: run {
            _error.trySend("지역은 필수입니다.")
            return
        }
        val title = this.title.value
        val content = this.content.value
        val location = this.location.value

        viewModelScope.launch(Dispatchers.IO) {
            postRepository.writePost(
                category,
                title,
                content,
                date,
                mainRegion,
                subRegion,
                location
            ).collect { response ->
                when (response) {
                    is Response.Loading -> _writeLoading.value = true
                    is Response.Success -> {
                        _writeLoading.value = false
                        _showPostDetail.value = response.data
                    }
                    is Response.Failure -> {
                        _writeLoading.value = false
                        _error.trySend(ExceptionMapper.mapToView(response.e))
                    }
                }
            }
        }
    }

    companion object {
        const val CATEGORY_KEY = "CATEGORY_KEY"
        const val TITLE_KEY = "TITLE_KEY"
        const val MAIN_REGION_KEY = "MAIN_REGION_KEY"
        const val SUB_REGION_KEY = "SUB_REGION_KEY"
        const val CONTENT_KEY = "CONTENT_KEY"
        const val LOCATION_KEY = "LOCATION_KEY"
        const val DATE_KEY = "DATE_KEY"
    }
}