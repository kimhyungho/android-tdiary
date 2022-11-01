package com.hardy.yongbyung.foundation

import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import com.hardy.yongbyung.R

object Icon {
    @Retention(AnnotationRetention.SOURCE)
    @IntDef(
        value = [
            ic_check,
            ic_check_filled,
            ic_select,
            ic_arrow_bottom,
            ic_close
        ]
    )
    annotation class Iconography

    const val ic_check = 0
    const val ic_check_filled = 1
    const val ic_select = 2
    const val ic_arrow_bottom = 3
    const val ic_close = 4

    @DrawableRes
    fun getIconDrawable(@Iconography icon: Int): Int {
        return when (icon) {
            ic_check -> R.drawable.ic_check
            ic_check_filled -> R.drawable.ic_check_filled
            ic_select -> R.drawable.ic_select
            ic_arrow_bottom -> R.drawable.ic_arrow_bottom
            ic_close -> R.drawable.ic_close
            else -> ic_check
        }
    }
}