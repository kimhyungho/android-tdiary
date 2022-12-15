package com.hardy.domain.interactors.posts

import com.hardy.domain.interactors.SuspendFlowUseCase
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.PostsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeletePostUseCase @Inject constructor(
    private val repo: PostsRepository
) : SuspendFlowUseCase<String, Void?> {
    override suspend fun invoke(params: String?): Flow<Response<Void?>> =
        repo.deletePostFromFirestore(
            params ?: throw IllegalArgumentException("params can not be null")
        )
}