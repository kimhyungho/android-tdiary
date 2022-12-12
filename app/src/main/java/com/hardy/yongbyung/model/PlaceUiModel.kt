package com.hardy.yongbyung.model

import com.hardy.domain.model.Place

data class PlaceUiModel(
    val id: Long? = null,
    val placeName: String? = null,
    val addressName: String? = null,
    val roadAddressName: String? = null,
    val placeUrl: String? = null,
    val x: Double? = null,
    val y: Double? = null,
    val isSelected: Boolean = false
) {
    fun toDomain(): Place {
        return Place(
            id ?: -1,
            placeName ?: "정해진 장소가 없어요",
            addressName,
            roadAddressName,
            placeUrl,
            x,
            y
        )
    }
}