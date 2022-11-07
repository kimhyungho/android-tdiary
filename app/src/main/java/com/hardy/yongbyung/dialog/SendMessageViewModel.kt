package com.hardy.yongbyung.dialog

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.MessageRepository
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
class SendMessageViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    val message: StateFlow<String> = savedStateHandle.getStateFlow(MESSAGE_KEY, "")

    private val _sendLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val sendLoading: StateFlow<Boolean> = _sendLoading

    private val _sendSuccess = BroadcastChannel<String>(Channel.BUFFERED)
    val sendSuccess = _sendSuccess.asFlow()

    private val _error = BroadcastChannel<String>(Channel.BUFFERED)
    val error = _error.asFlow()

    fun setMessage(message: CharSequence) {
        savedStateHandle[MESSAGE_KEY] = message.toString()
    }

    fun sendMessage(receiverUid: String) = viewModelScope.launch(Dispatchers.IO) {
        val message = message.value
        messageRepository.sendMessage(receiverUid, message).collect { response ->
            when (response) {
                is Response.Loading -> _sendLoading.value = true
                is Response.Success -> {
                    _sendLoading.value = false
                    _sendSuccess.trySend(response.data!!)
                }
                is Response.Failure -> {
                    _sendLoading.value = false
                    _error.trySend(ExceptionMapper.mapToView(response.e))
                }
            }
        }
    }

    companion object {
        const val MESSAGE_KEY = "MESSAGE_KEY"
    }
}