package com.hardy.yongbyung.ui.editprofile

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.AuthRepository
import com.hardy.domain.repositories.UserRepository
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
class EditProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    val nickname: StateFlow<String> = savedStateHandle.getStateFlow(NICKNAME_KEY, "")
    val profileImage: StateFlow<String> = savedStateHandle.getStateFlow(PROFILE_IMAGE_KEY, "")

    private val _originNickname: MutableStateFlow<String> = MutableStateFlow("")
    val originNickname: StateFlow<String> = _originNickname

    private val _meLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val meLoading: StateFlow<Boolean> = _meLoading

    private val _changeLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val changeLoading: StateFlow<Boolean> = _changeLoading

    private val _message = BroadcastChannel<String>(Channel.BUFFERED)
    val message = _message.asFlow()

    init {
        getMe()
    }

    fun setNickname(nickname: CharSequence) {
        savedStateHandle[NICKNAME_KEY] = nickname.toString()
    }

    fun setNickname(nickname: String) {
        savedStateHandle[NICKNAME_KEY] = nickname
    }

    fun setProfileImage(uri: Uri?) {
        uri?.let { savedStateHandle[PROFILE_IMAGE_KEY] = uri.toString() }
    }

    fun setProfileImage(uri: String) {
        savedStateHandle[PROFILE_IMAGE_KEY] = uri
    }

    private fun getMe() = viewModelScope.launch(Dispatchers.IO) {
        authRepository.getMe().collect { response ->
            when (response) {
                is Response.Loading -> _meLoading.value = true
                is Response.Success -> {
                    _meLoading.value = false
                    response.data?.let {
                        it.nickname?.let { nickname -> _originNickname.value = nickname }
                        it.profileImage?.let { url -> setProfileImage(url) }
                    }
                }

                is Response.Failure -> {
                    _meLoading.value = false
                    _message.trySend(ExceptionMapper.mapToView(response.e))
                }
            }
        }
    }

    fun onChangeButtonClick() = viewModelScope.launch(Dispatchers.IO) {
        val nickname = nickname.value
        val profileImage = if (profileImage.value.startsWith("http")) null
        else profileImage.value
        authRepository.editProfile(nickname, profileImage?.toUri()).collect { response ->
            when (response) {
                is Response.Loading -> _changeLoading.value = true
                is Response.Success -> {
                    _changeLoading.value = false
                    _message.trySend("프로필이 변경되었습니다.")
                }
                is Response.Failure -> {
                    _changeLoading.value = false
                    _message.trySend(ExceptionMapper.mapToView(response.e))
                }
            }
        }
    }

    companion object {
        const val NICKNAME_KEY = "NICKNAME_KEY"
        const val PROFILE_IMAGE_KEY = "PROFILE_IMAGE_KEY"
    }
}