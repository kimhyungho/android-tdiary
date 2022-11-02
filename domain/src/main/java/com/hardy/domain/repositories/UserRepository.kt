package com.hardy.domain.repositories

import com.hardy.domain.model.Response
import com.hardy.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(uid: String): Flow<Response<User.Registered>>
}