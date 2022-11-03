package com.hardy.yongbyung.ui.main

import androidx.lifecycle.viewModelScope
import com.hardy.domain.model.MessageRoom
import com.hardy.domain.repositories.MessageRepository
import com.hardy.yongbyung.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val messageRepository: MessageRepository
) : BaseViewModel() {
    private val _chatRooms: MutableStateFlow<List<Pair<String?, MessageRoom?>>> = MutableStateFlow(listOf())

    fun getMessageRooms() = viewModelScope.launch(Dispatchers.IO) {
        messageRepository.getMessageRooms().collect {

        }
    }
}