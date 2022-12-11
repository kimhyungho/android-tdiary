package com.hardy.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class GetLocalSearchKeywordResponse(
    @SerializedName("documents")
    val documents: List<Document>? = null
) {
    data class Document(
        @SerializedName("id")
        val id: String? = null,
        @SerializedName("place_name")
        val placeName: String? = null,
        @SerializedName("address_name")
        val addressName: String? = null,
        @SerializedName("category_name")
        val categoryName: String? = null,
        @SerializedName("place_url")
        val placeUrl: String? = null,
        @SerializedName("road_address_name")
        val roadAddressName: String? = null,
        @SerializedName("x")
        val x: String? = null,
        @SerializedName("y")
        val y: String? = null
    )
}