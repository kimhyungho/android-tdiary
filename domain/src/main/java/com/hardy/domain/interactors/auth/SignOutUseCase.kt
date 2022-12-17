package com.hardy.domain.interactors.auth

import com.hardy.domain.interactors.SuspendFlowUseCase
import com.hardy.domain.model.Response
import com.hardy.domain.model.User
import com.hardy.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignOutUseCase @Inject constructor(
    private val repo: AuthRepository
) : SuspendFlowUseCase<Unit, User> {
    override suspend fun invoke(params: Unit?): Flow<Response<User>> {
        return repo.signOut()
    }
}