package com.hardy.yongbyung.ui.editprofile

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.AuthRepository
import com.hardy.domain.repositories.UserRepository
import com.hardy.yongbyung.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    val nickname: StateFlow<String> = savedStateHandle.getStateFlow(NICKNAME_KEY, "")
    val profileImage: StateFlow<String> = savedStateHandle.getStateFlow(PROFILE_IMAGE_KEY, "")

    private val _originNickname: MutableStateFlow<String> = MutableStateFlow("")
    val originNickname: StateFlow<String> = _originNickname

    private val _showBack: MutableStateFlow<Unit?> = MutableStateFlow(null)
    val showBack: StateFlow<Unit?> = _showBack

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
                is Response.Loading -> {

                }

                is Response.Success -> {
                    response.data?.let {
                        it.nickname?.let { nickname -> _originNickname.value = nickname }
                        it.profileImage?.let { url -> setProfileImage(url) }
                    }
                }

                is Response.Failure -> {

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
                is Response.Loading -> {

                }

                is Response.Success -> {
                    _showBack.value = Unit
                }

                is Response.Failure -> {

                }
            }
        }
    }

    companion object {
        const val NICKNAME_KEY = "NICKNAME_KEY"
        const val PROFILE_IMAGE_KEY = "PROFILE_IMAGE_KEY"
    }
}