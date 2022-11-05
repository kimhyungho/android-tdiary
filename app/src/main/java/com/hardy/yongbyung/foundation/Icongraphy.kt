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
            ic_close,
            ic_calendar,
            ic_location,
            ic_heart,
            ic_heart_filled,
            ic_back,
            ic_send,
            ic_menu
        ]
    )
    annotation class Iconography

    const val ic_check = 0
    const val ic_check_filled = 1
    const val ic_select = 2
    const val ic_arrow_bottom = 3
    const val ic_close = 4
    const val ic_calendar = 5
    const val ic_location = 6
    const val ic_heart = 7
    const val ic_heart_filled = 8
    const val ic_back = 9
    const val ic_send = 10
    const val ic_menu = 11

    @DrawableRes
    fun getIconDrawable(@Iconography icon: Int): Int {
        return when (icon) {
            ic_check -> R.drawable.ic_check
            ic_check_filled -> R.drawable.ic_check_filled
            ic_select -> R.drawable.ic_select
            ic_arrow_bottom -> R.drawable.ic_arrow_bottom
            ic_close -> R.drawable.ic_close
            ic_calendar -> R.drawable.ic_calendar
            ic_location -> R.drawable.ic_location
            ic_heart -> R.drawable.ic_heart
            ic_heart_filled -> R.drawable.ic_heart_filled
            ic_back -> R.drawable.ic_back
            ic_send -> R.drawable.ic_send
            ic_menu -> R.drawable.ic_menu
            else -> ic_check
        }
    }
}