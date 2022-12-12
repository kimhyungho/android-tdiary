package com.hardy.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Place(
    val id: Long?,
    val placeName: String?,
    val addressName: String?,
    val roadAddressName: String?,
    val placeUrl: String?,
    val x: Double?,
    val y: Double?
): Parcelable