package com.hardy.yongbyung.ui.messagedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.MessageRepository
import com.hardy.yongbyung.model.MessageUiMapper
import com.hardy.yongbyung.model.MessageUiModel
import com.hardy.yongbyung.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageDetailViewModel @Inject constructor(
    private val messageUiMapper: MessageUiMapper,
    private val savedStateHandle: SavedStateHandle,
    private val messageRepository: MessageRepository
) : BaseViewModel() {
    private val _messages: MutableStateFlow<List<MessageUiModel>> = MutableStateFlow(listOf())
    val messages: StateFlow<List<MessageUiModel>> = _messages

    init {
        savedStateHandle.get<String>("messageRoomId")?.let { messageRoomId ->
            getMessages(messageRoomId)
        }
    }

    private fun getMessages(messageRoomId: String) = viewModelScope.launch(Dispatchers.IO) {
        messageRepository.getMessages(messageRoomId).collect { response ->
            when (response) {
                is Response.Loading -> {

                }

                is Response.Success -> {
                    response.data?.let {
                        _messages.value = it.map(messageUiMapper::mapToView)
                    }
                }

                is Response.Failure -> {

                }
            }
        }
    }
}