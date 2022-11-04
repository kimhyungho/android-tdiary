package com.hardy.yongbyung.ui.main

import androidx.lifecycle.viewModelScope
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.MessageRepository
import com.hardy.yongbyung.model.MessageRoomUiMapper
import com.hardy.yongbyung.model.MessageRoomUiModel
import com.hardy.yongbyung.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val messageRoomUiMapper: MessageRoomUiMapper,
    private val messageRepository: MessageRepository
) : BaseViewModel() {
    private val _messageRooms: MutableStateFlow<List<MessageRoomUiModel>> =
        MutableStateFlow(listOf())
    val messageRooms: StateFlow<List<MessageRoomUiModel>> = _messageRooms

    private val _hasNewMessage: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val hasNewMessage: StateFlow<Boolean> = _hasNewMessage

    init {
        getMessageRooms()
    }

    fun getMessageRooms() = viewModelScope.launch(Dispatchers.IO) {
        messageRepository.getMessageRooms().collect { response ->
            when (response) {
                is Response.Loading -> {

                }

                is Response.Success -> {
                    response.data?.let {
                        response.data?.map {
                            messageRoomUiMapper.mapToView(it)
                        }?.let { _messageRooms.value = it }
                    }
                }

                is Response.Failure -> {

                }
            }
        }
    }
}