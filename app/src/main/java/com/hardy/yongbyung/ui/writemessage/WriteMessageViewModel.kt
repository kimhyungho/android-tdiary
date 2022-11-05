package com.hardy.yongbyung.ui.writemessage

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
class WriteMessageViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val opponentUid = savedStateHandle.get<String>("uid")

    private val _sendSuccess = BroadcastChannel<Unit>(Channel.BUFFERED)
    val sendSuccess = _sendSuccess.asFlow()

    private val _error = BroadcastChannel<String>(Channel.BUFFERED)
    val error = _error.asFlow()

    private val _sendLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val sendLoading: StateFlow<Boolean> = _sendLoading

    val message: StateFlow<String> = savedStateHandle.getStateFlow(MESSAGE_KEY, "")

    fun setMessage(message: CharSequence) {
        savedStateHandle[MESSAGE_KEY] = message.toString()
    }

    fun onSendButtonClick() = viewModelScope.launch(Dispatchers.IO) {
        opponentUid ?: run {
            // snackBar
            return@launch
        }
        messageRepository.sendMessage(opponentUid, message.value).collect { response ->
            when (response) {
                is Response.Loading -> _sendLoading.value = true
                is Response.Success -> {
                    _sendLoading.value = false
                    _sendSuccess.send(Unit)
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