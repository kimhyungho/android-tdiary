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
            ic_menu,
            ic_arrow_top,
            ic_logout,
            ic_sign_out,
            ic_term,
            ic_post,
            ic_siren,
            ic_plus,
            ic_setting,
            ic_delete,
            ic_gallery
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
    const val ic_arrow_top = 12
    const val ic_logout = 13
    const val ic_sign_out = 14
    const val ic_term = 15
    const val ic_post = 16
    const val ic_siren = 17
    const val ic_plus = 18
    const val ic_setting = 19
    const val ic_delete = 20
    const val ic_gallery = 21

    @DrawableRes
    fun getIconDrawable(@Iconography icon: Int): Int {
        return when (icon) {
            ic_check -> R.drawable.ic_check
            ic_check_filled -> R.drawable.ic_check_filled
            ic_select -> R.drawable.ic_select
            ic_arrow_bottom -> R.drawable.ic_arrow_bottom
            ic_calendar -> R.drawable.ic_calendar
            ic_location -> R.drawable.ic_location
            ic_heart -> R.drawable.ic_heart
            ic_heart_filled -> R.drawable.ic_heart_filled
            ic_back -> R.drawable.ic_back
            ic_send -> R.drawable.ic_send
            ic_menu -> R.drawable.ic_menu
            ic_arrow_top -> R.drawable.ic_arrow_top
            ic_logout -> R.drawable.ic_logout
            ic_sign_out -> R.drawable.ic_sign_out
            ic_term -> R.drawable.ic_term
            ic_post -> R.drawable.ic_post
            ic_siren -> R.drawable.ic_siren
            ic_plus -> R.drawable.ic_plus
            ic_setting -> R.drawable.ic_setting
            ic_delete -> R.drawable.ic_delete
            ic_gallery -> R.drawable.ic_gallery
            else -> ic_check
        }
    }
}