package com.hardy.yongbyung.model

import com.hardy.domain.model.Place

data class PlaceUiModel(
    val id: Long?,
    val placeName: String?,
    val addressName: String?,
    val roadAddressName: String?,
    val placeUrl: String?,
    val x: Double?,
    val y: Double?,
    val isSelected: Boolean
) {
    fun toDomain(): Place {
        return Place(id, placeName, addressName, roadAddressName, placeUrl, x, y)
    }
}