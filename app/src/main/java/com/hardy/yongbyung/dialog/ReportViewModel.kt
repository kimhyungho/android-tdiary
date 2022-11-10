package com.hardy.yongbyung.dialog

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.ReportRepository
import com.hardy.yongbyung.mapper.ExceptionMapper
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
class ReportViewModel @Inject constructor(
    private val reportRepository: ReportRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val title: StateFlow<String> = savedStateHandle.getStateFlow(TITLE_KEY, "")
    private val content: StateFlow<String> = savedStateHandle.getStateFlow(CONTENT_KEY, "")

    private val _reportSuccess: MutableStateFlow<Unit?> = MutableStateFlow(null)
    val reportSuccess: StateFlow<Unit?> = _reportSuccess

    private val _reportLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val reportLoading: StateFlow<Boolean> = _reportLoading

    private val _error = BroadcastChannel<String>(Channel.BUFFERED)
    val error = _error.asFlow()

    fun setTitle(title: CharSequence) {
        savedStateHandle[TITLE_KEY] = title.toString()
    }

    fun setContent(content: CharSequence) {
        savedStateHandle[CONTENT_KEY] = content.toString()
    }

    fun reportPost(postId: String) = viewModelScope.launch(Dispatchers.IO) {
        val title = title.value
        val content = content.value

        reportRepository.reportPost(postId, title, content).collect { response ->
            when (response) {
                is Response.Loading -> _reportLoading.value = true
                is Response.Success -> {
                    _reportLoading.value = false
                    _reportSuccess.value = Unit
                }
                is Response.Failure -> {
                    _reportLoading.value = false
                    _error.trySend(ExceptionMapper.mapToView(response.e))
                }
            }
        }
    }

    companion object {
        private const val TITLE_KEY = "TITLE_KEY"
        private const val CONTENT_KEY = "CONTENT_KEY"
    }
}