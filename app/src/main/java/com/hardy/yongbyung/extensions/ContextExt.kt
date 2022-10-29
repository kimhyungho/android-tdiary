package com.hardy.yongbyung.extensions

import android.content.Context
import android.util.TypedValue
import androidx.annotation.DimenRes
import kotlin.math.roundToInt

fun Context.dpToIntPx(dp: Float): Int {
    val displayMetrics = this.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics).roundToInt()
}

fun Context.getDimenFloat(@DimenRes dimenRes: Int): Float = this.resources.getDimension(dimenRes)