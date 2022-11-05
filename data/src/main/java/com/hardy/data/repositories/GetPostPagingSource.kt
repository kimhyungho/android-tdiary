package com.hardy.data.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.hardy.domain.model.Post
import kotlinx.coroutines.tasks.await

class GetPostPagingSource(
    private val queryPostsByName: Query
) : PagingSource<QuerySnapshot, Pair<String, Post>>() {
    override fun getRefreshKey(state: PagingState<QuerySnapshot, Pair<String, Post>>): QuerySnapshot? =
        null

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Pair<String, Post>> {
        return try {
            val currentPage = params.key ?: queryPostsByName.get().await()
            val nextKey = if (currentPage.isEmpty) {
                null
            } else {
                val lastVisibleProduct = currentPage.documents[currentPage.size() - 1]
                val nextPage = queryPostsByName.startAfter(lastVisibleProduct).get().await()
                if (nextPage.size() > 0) nextPage else null
            }
            LoadResult.Page(
                data = currentPage.map { Pair(it.id, it.toObject(Post::class.java)) },
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}