package com.hardy.domain.repositories

import com.hardy.domain.model.Place
import com.hardy.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface KakaoMapRepository {
    fun getPlaceByQuery(query: String): Flow<Response<List<Place>>>
}