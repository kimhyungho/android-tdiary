package com.hardy.domain.interactors.kakao

import com.hardy.domain.interactors.FlowUseCase
import com.hardy.domain.model.Place
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.KakaoMapRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPlaceByQueryUseCase @Inject constructor(
    private val repo: KakaoMapRepository
) : FlowUseCase<String, List<Place>> {
    override fun invoke(params: String?): Flow<Response<List<Place>>> {
        return repo.getPlaceByQuery(
            params ?: throw IllegalArgumentException("params can not be null")
        )
    }
}