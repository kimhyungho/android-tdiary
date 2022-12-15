package com.hardy.domain.interactors.posts

import android.net.Uri
import com.hardy.domain.interactors.SuspendFlowUseCase
import com.hardy.domain.model.Place
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.PostsRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddPostUseCase @Inject constructor(
    private val repo: PostsRepository
) : SuspendFlowUseCase<AddPostUseCase.Params, Void?> {
    override suspend fun invoke(params: Params?): Flow<Response<Void?>> {
        val (date, title, place, story, mediaUri, mimeType) = params
            ?: throw IllegalArgumentException("params can not be null")

        return repo.addPostToFirestore(date, title, place, story, mediaUri, mimeType)
    }

    data class Params(
        val date: Date,
        val title: String,
        val place: Place?,
        val story: String,
        val mediaUri: Uri?,
        val mimeType: String?
    )
}