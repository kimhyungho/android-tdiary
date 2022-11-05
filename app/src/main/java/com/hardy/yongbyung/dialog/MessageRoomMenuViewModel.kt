package com.hardy.yongbyung.dialog

import androidx.lifecycle.viewModelScope
import com.hardy.data.repositories.MessageRoomRepositoryImpl
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.MessageRepository
import com.hardy.domain.repositories.MessageRoomRepository
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
class MessageRoomMenuViewModel @Inject constructor(
    private val messageRoomRepository: MessageRoomRepository
) : BaseViewModel() {
    private val _deleteLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val deleteLoading: StateFlow<Boolean> = _deleteLoading

    private val _deleteSuccess = BroadcastChannel<Unit>(Channel.BUFFERED)
    val deleteSuccess = _deleteSuccess.asFlow()

    private val _error = BroadcastChannel<String>(Channel.BUFFERED)
    val error = _error.asFlow()

    fun onDeleteButtonClick(messageRoomId: String) = viewModelScope.launch(Dispatchers.IO) {
        messageRoomRepository.deleteMessageRoom(messageRoomId).collect { response ->
            when (response) {
                is Response.Loading -> _deleteLoading.value = true

                is Response.Success -> {
                    _deleteLoading.value = false
                    _deleteSuccess.trySend(Unit)
                }

                is Response.Failure -> {
                    _deleteLoading.value = false
                    _error.trySend(ExceptionMapper.mapToView(response.e))
                }
            }
        }
    }
}