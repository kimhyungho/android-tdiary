package com.hardy.data.remote.api

import com.hardy.data.remote.model.response.GetLocalSearchKeywordResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoService {
    @GET("/v2/local/search/keyword.json")
    suspend fun getLocalSearchKeyword(
        @Query("query")
        query: String
    ): GetLocalSearchKeywordResponse
}