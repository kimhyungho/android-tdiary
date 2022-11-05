package com.hardy.yongbyung.ui.messagedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.MessageRepository
import com.hardy.yongbyung.mapper.ExceptionMapper
import com.hardy.yongbyung.model.MessageUiMapper
import com.hardy.yongbyung.model.MessageUiModel
import com.hardy.yongbyung.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ObsoleteCoroutinesApi::class)
@HiltViewModel
class MessageDetailViewModel @Inject constructor(
    private val messageUiMapper: MessageUiMapper,
    private val savedStateHandle: SavedStateHandle,
    private val messageRepository: MessageRepository
) : BaseViewModel() {
    val opponentUid = savedStateHandle.get<String>("uid")
    val messageRoomUid = savedStateHandle.get<String>("messageRoomId")

    private val _messages: MutableStateFlow<List<MessageUiModel>> = MutableStateFlow(listOf())
    val messages: StateFlow<List<MessageUiModel>> = _messages

    private val _messageLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val messageLoading: StateFlow<Boolean> = _messageLoading

    private val _error = BroadcastChannel<String>(Channel.BUFFERED)
    val error = _error.asFlow()

    init {
        messageRoomUid?.let { messageRoomId ->
            getMessages(messageRoomId)
        }
    }

    private fun getMessages(messageRoomId: String) = viewModelScope.launch(Dispatchers.IO) {
        messageRepository.getMessages(messageRoomId).collect { response ->
            when (response) {
                is Response.Loading -> _messageLoading.value = true
                is Response.Success -> {
                    _messageLoading.value = false
                    response.data?.let {
                        _messages.value = it.map(messageUiMapper::mapToView)
                    }
                }
                is Response.Failure -> {
                    _messageLoading.value = false
                    _error.trySend(ExceptionMapper.mapToView(response.e))
                }
            }
        }
    }
}