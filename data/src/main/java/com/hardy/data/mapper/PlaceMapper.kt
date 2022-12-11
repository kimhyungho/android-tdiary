package com.hardy.data.mapper

import com.hardy.data.remote.model.response.GetLocalSearchKeywordResponse
import com.hardy.domain.model.Place

object PlaceMapper : Mapper<GetLocalSearchKeywordResponse, List<Place>> {
    override fun mapToDomain(from: GetLocalSearchKeywordResponse): List<Place> {
        return from.documents?.map {
            Place(
                id = it.id?.toLongOrNull(),
                placeName = it.placeUrl,
                addressName = it.addressName,
                roadAddressName = it.roadAddressName,
                x = it.x?.toDoubleOrNull(),
                y = it.y?.toDoubleOrNull()
            )
        } ?: listOf()
    }
}