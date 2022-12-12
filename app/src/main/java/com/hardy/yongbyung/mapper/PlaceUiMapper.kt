package com.hardy.yongbyung.mapper

import com.hardy.domain.model.Place
import com.hardy.yongbyung.model.PlaceUiModel

object PlaceUiMapper : Mapper<Place, PlaceUiModel> {
    override fun mapToView(from: Place): PlaceUiModel {
        return PlaceUiModel(
            id = from.id,
            placeName = from.placeName,
            addressName = from.addressName,
            roadAddressName = from.roadAddressName,
            placeUrl = from.placeUrl,
            x = from.x,
            y = from.y
        )
    }
}