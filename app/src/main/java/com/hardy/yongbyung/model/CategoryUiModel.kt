package com.hardy.yongbyung.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryUiModel(
    val name: String,
    val drawableId: Int,
    val isSelected: Boolean
) : Parcelable