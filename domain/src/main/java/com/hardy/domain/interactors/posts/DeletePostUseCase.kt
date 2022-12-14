package com.hardy.domain.interactors.posts

import com.hardy.domain.interactors.SuspendFlowUseCase
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.PostsRepository
import kotlinx.coroutines.flow.Flow

class DeletePostUseCase(
    private val repo: PostsRepository
) : SuspendFlowUseCase<String, Void?> {
    override suspend fun invoke(params: String?): Flow<Response<Void?>> =
        repo.deletePostFromFirestore(
            params ?: throw IllegalArgumentException("params can not be null")
        )
}