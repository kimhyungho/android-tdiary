package com.hardy.yongbyung.ui.profile

import com.hardy.domain.repositories.PostRepository
import com.hardy.domain.repositories.UserRepository
import com.hardy.yongbyung.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) : BaseViewModel() {



}