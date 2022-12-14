package com.hardy.domain.interactors.posts

import android.net.Uri
import com.hardy.domain.interactors.SuspendFlowUseCase
import com.hardy.domain.model.Place
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.PostsRepository
import kotlinx.coroutines.flow.Flow

class AddPostUseCase(
    private val repo: PostsRepository
) : SuspendFlowUseCase<AddPostUseCase.Params, Void?> {
    override suspend fun invoke(params: Params?): Flow<Response<Void?>> {
        val (title, content, place) = params
            ?: throw IllegalArgumentException("params can not be null")

        return repo.addPostToFirestore(title, content, place, null, null, null)
    }

    data class Params(
        val title: String,
        val content: String,
        val place: Place,
        val mediaUri1: Uri?,
        val mediaUri2: Uri?,
        val mediaUri3: Uri?
    )
}