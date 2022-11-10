package com.hardy.domain.repositories

import com.hardy.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface ReportRepository {
    fun reportPost(postId: String, title: String, content: String): Flow<Response<Unit>>
}