package com.hardy.data.repositories

import com.hardy.data.mapper.PlaceMapper
import com.hardy.data.remote.api.KakaoService
import com.hardy.domain.model.Place
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.KakaoMapRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KakaoMapRepositoryImpl @Inject constructor(
    private val kakaoService: KakaoService
) : KakaoMapRepository {
    override fun getPlaceByQuery(query: String): Flow<Response<List<Place>>> = flow {
        try {
            val places = PlaceMapper.mapToDomain(kakaoService.getLocalSearchKeyword(query))
            emit(Response.Success(places))
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }
}