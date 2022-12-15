package com.hardy.yongbyung.ui.writepost

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hardy.domain.interactors.posts.AddPostUseCase
import com.hardy.domain.model.Place
import com.hardy.domain.model.Response
import com.hardy.yongbyung.ui.base.BaseViewModel
import com.hardy.yongbyung.utils.DateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WritePostViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val addPostUseCase: AddPostUseCase
) : BaseViewModel() {
    val date: String = savedStateHandle.get<String>(DATE_KEY)!!
    val title: StateFlow<String> = savedStateHandle.getStateFlow(TITLE_KEY, "")
    val place: StateFlow<Place?> = savedStateHandle.getStateFlow(PLACE_KEY, null)
    val story: StateFlow<String> = savedStateHandle.getStateFlow(STORY_KEY, "")
    val media: StateFlow<Uri?> = savedStateHandle.getStateFlow(MEDIA_KEY, null)
    val mimeType: StateFlow<String?> = savedStateHandle.getStateFlow(MIME_TYPE_KEY, null)

    private val _writeLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val writeLoading: StateFlow<Boolean> = _writeLoading

    fun setTitle(title: CharSequence) {
        savedStateHandle[TITLE_KEY] = title.toString()
    }

    fun setPlace(place: Place?) {
        savedStateHandle[PLACE_KEY] = place
    }

    fun setStory(story: CharSequence) {
        savedStateHandle[STORY_KEY] = story.toString()
    }

    fun setMedia(uri: Uri?, mimeType: String?) {
        savedStateHandle[MEDIA_KEY] = uri
        savedStateHandle[MIME_TYPE_KEY] = mimeType
    }

    fun writePost() = viewModelScope.launch {
        addPostUseCase(
            AddPostUseCase.Params(
                DateUtil.stringToDate(date)!!,
                title.value,
                place.value,
                story.value,
                media.value,
                mimeType.value
            )
        ).collect { response ->
            when (response) {
                is Response.Loading -> _writeLoading.value = true
                is Response.Success -> {
                    Log.d("kkkk", "success")
                    _writeLoading.value = false
                }
                is Response.Failure -> {
                    Log.d("kkkk", "dfd", response.e)
                    _writeLoading.value = false
                }
            }
        }
    }

    companion object {
        const val DATE_KEY = "date"
        const val TITLE_KEY = "title"
        const val PLACE_KEY = "place"
        const val STORY_KEY = "story"
        const val MEDIA_KEY = "media"
        const val MIME_TYPE_KEY = "mimeType"
    }
}