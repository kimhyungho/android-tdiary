package com.hardy.yongbyung.dialog

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.MessageRepository
import com.hardy.yongbyung.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendMessageViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    val message: StateFlow<String> = savedStateHandle.getStateFlow(MESSAGE_KEY, "")

    fun setMessage(message: CharSequence) {
        savedStateHandle[MESSAGE_KEY] = message.toString()
    }

    fun sendMessage(receiverUid: String) = viewModelScope.launch(Dispatchers.IO) {
        val message = message.value
        messageRepository.sendMessage(receiverUid, message).collect { response ->
            when (response) {
                is Response.Loading -> {

                }

                is Response.Success -> {

                }

                is Response.Failure -> {
                    Log.d("kkkk", "herehere", response.e)
                }
            }
        }
    }

    companion object {
        const val MESSAGE_KEY = "MESSAGE_KEY"
    }
}