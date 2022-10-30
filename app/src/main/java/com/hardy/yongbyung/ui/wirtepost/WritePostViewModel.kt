package com.hardy.yongbyung.ui.wirtepost

import androidx.lifecycle.SavedStateHandle
import com.hardy.domain.repositories.PostRepository
import com.hardy.yongbyung.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class WritePostViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val savedStateHandle: SavedStateHandle
): BaseViewModel() {
    val category: StateFlow<String> = savedStateHandle.getStateFlow(CATEGORY_KEY, "")
    val title: StateFlow<String> = savedStateHandle.getStateFlow(TITLE_KEY, "")
    val content: StateFlow<String> = savedStateHandle.getStateFlow(CONTENT_KEY, "")
    val date: StateFlow<Date?> = savedStateHandle.getStateFlow(DATE_KEY, null)

    fun onCategorySelected() {
    }

    fun onTitleTextChanged(title: String) {
        savedStateHandle[TITLE_KEY] = title
    }

    fun onContentTextChanged(content: String) {
        savedStateHandle[CONTENT_KEY] = content
    }

    fun onDateSelected() {
//        savedStateHandle[DATE_KEY] =
    }

    fun onWriteButtonClick() {

    }


    companion object {
        const val CATEGORY_KEY = "CATEGORY_KEY"
        const val TITLE_KEY = "TITLE_KEY"
        const val CONTENT_KEY = "CONTENT_KEY"
        const val DATE_KEY = "DATE_KEY"
    }
}