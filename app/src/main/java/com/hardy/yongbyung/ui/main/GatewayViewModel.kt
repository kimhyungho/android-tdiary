package com.hardy.yongbyung.ui.main

import androidx.lifecycle.viewModelScope
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.MessageRepository
import com.hardy.yongbyung.mapper.ExceptionMapper
import com.hardy.yongbyung.model.MessageRoomUiMapper
import com.hardy.yongbyung.model.MessageRoomUiModel
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
class GatewayViewModel @Inject constructor(
    private val messageRoomUiMapper: MessageRoomUiMapper,
    private val messageRepository: MessageRepository
) : BaseViewModel() {
    private val _messageRooms: MutableStateFlow<List<MessageRoomUiModel>> =
        MutableStateFlow(listOf())
    val messageRooms: StateFlow<List<MessageRoomUiModel>> = _messageRooms

    private val _hasNewMessage: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val hasNewMessage: StateFlow<Boolean> = _hasNewMessage

    private val _messageRoomLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val messageRoomLoading: StateFlow<Boolean> = _messageRoomLoading

    private val _refreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val refreshing: StateFlow<Boolean> = _refreshing

    private val _showEmptyImage: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showEmptyImage: StateFlow<Boolean> = _showEmptyImage

    private val _error = BroadcastChannel<String>(Channel.BUFFERED)
    val error = _error.asFlow()

    init {
        getMessageRooms()
    }

    private fun getMessageRooms() = viewModelScope.launch(Dispatchers.IO) {
        messageRepository.getMessageRooms().collect { response ->
            when (response) {
                is Response.Loading -> {
                    _showEmptyImage.value = false
                    _messageRoomLoading.value = true
                }
                is Response.Success -> {
                    _messageRoomLoading.value = false
                    _refreshing.value = false
                    response.data?.let {
                        response.data?.map {
                            messageRoomUiMapper.mapToView(it)
                        }?.sortedByDescending { it.lastMessageDate }
                            ?.let {
                                _showEmptyImage.value = it.isEmpty()
                                _messageRooms.value = it
                            }
                    }
                }
                is Response.Failure -> {
                    _messageRoomLoading.value = false
                    _refreshing.value = false
                    _error.trySend(ExceptionMapper.mapToView(response.e))
                }
            }
        }
    }

    fun onRefresh() {
        _refreshing.value = true
        getMessageRooms()
    }
}