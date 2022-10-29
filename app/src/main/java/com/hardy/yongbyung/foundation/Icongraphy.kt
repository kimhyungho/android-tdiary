package com.hardy.yongbyung.foundation

import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import com.hardy.yongbyung.R

object Icon {
    @Retention(AnnotationRetention.SOURCE)
    @IntDef(
        value = [
            ic_check,
            ic_check_filled
        ]
    )
    annotation class Iconography

    const val ic_check = 0
    const val ic_check_filled = 1

    @DrawableRes
    fun getIconDrawable(@Iconography icon: Int): Int {
        return when (icon) {
            ic_check -> R.drawable.ic_check
            ic_check_filled -> R.drawable.ic_check_filled
            else -> ic_check
        }
    }
}