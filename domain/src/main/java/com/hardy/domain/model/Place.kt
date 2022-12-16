package com.hardy.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Place(
    val id: Long? = null,
    val placeName: String? = null,
    val addressName: String? = null,
    val roadAddressName: String? = null,
    val placeUrl: String? = null,
    val x: Double? = null,
    val y: Double? = null
) : Parcelable