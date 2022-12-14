package com.hardy.domain.interactors.posts

import com.hardy.domain.interactors.FlowUseCase
import com.hardy.domain.model.Post
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.PostsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPostsByUidUseCase @Inject constructor(
    private val repo: PostsRepository
) : FlowUseCase<String, List<Post>> {
    override fun invoke(params: String?): Flow<Response<List<Post>>> =
        repo.getPostsByUidFromFirestore(params)
}