package com.hardy.yongbyung.foundation

import androidx.annotation.DimenRes
import androidx.annotation.IntDef
import androidx.annotation.StyleRes
import com.hardy.yongbyung.R
import com.hardy.yongbyung.foundation.Typo.B1
import com.hardy.yongbyung.foundation.Typo.B2
import com.hardy.yongbyung.foundation.Typo.B2_1
import com.hardy.yongbyung.foundation.Typo.Button
import com.hardy.yongbyung.foundation.Typo.Caption1
import com.hardy.yongbyung.foundation.Typo.Caption1_1
import com.hardy.yongbyung.foundation.Typo.Caption2
import com.hardy.yongbyung.foundation.Typo.Caption3
import com.hardy.yongbyung.foundation.Typo.Caption4
import com.hardy.yongbyung.foundation.Typo.Caption5
import com.hardy.yongbyung.foundation.Typo.H1
import com.hardy.yongbyung.foundation.Typo.H2
import com.hardy.yongbyung.foundation.Typo.H3
import com.hardy.yongbyung.foundation.Typo.H3_1
import com.hardy.yongbyung.foundation.Typo.H4
import com.hardy.yongbyung.foundation.Typo.Subtitle1
import com.hardy.yongbyung.foundation.Typo.Subtitle2
import com.hardy.yongbyung.foundation.Typo.Subtitle3
import com.hardy.yongbyung.foundation.Typo.Subtitle4


@Retention(AnnotationRetention.SOURCE)
@IntDef(value = [H1, H2, H3, H4, H3_1, B1, B2, B2_1, Button, Subtitle1, Subtitle2, Subtitle3, Subtitle4, Caption1, Caption1_1, Caption2, Caption3, Caption4, Caption5])
annotation class Typography

object Typo {
    const val H1 = 0
    const val H2 = 1
    const val H3 = 2
    const val H3_1 = 3
    const val H4 = 4
    const val B1 = 10
    const val B2 = 11
    const val B2_1 = 12
    const val Button = 20
    const val Subtitle1 = 30
    const val Subtitle2 = 31
    const val Subtitle3 = 32
    const val Subtitle4 = 33
    const val Caption1 = 40
    const val Caption1_1 = 41
    const val Caption2 = 42
    const val Caption3 = 43
    const val Caption4 = 44
    const val Caption5 = 45

    @StyleRes
    fun getStyle(@Typography typo: Int): Int = when (typo) {
        H1 -> R.style.Text_H1
        H2 -> R.style.Text_H2
        H3 -> R.style.Text_H3
        H3_1 -> R.style.Text_H3_1
        H4 -> R.style.Text_H4
        B1 -> R.style.Text_B1
        B2 -> R.style.Text_B2
        B2_1 -> R.style.Text_B2_1
        Button -> R.style.Text_Button
        Subtitle1 -> R.style.Text_Subtitle1
        Subtitle2 -> R.style.Text_Subtitle2
        Subtitle3 -> R.style.Text_Subtitle3
        Subtitle4 -> R.style.Text_Subtitle4
        Caption1 -> R.style.Text_Caption1
        Caption1_1 -> R.style.Text_Caption1_1
        Caption2 -> R.style.Text_Caption2
        Caption3 -> R.style.Text_Caption3
        Caption4 -> R.style.Text_Caption4
        Caption5 -> R.style.Text_Caption5
        else -> R.style.Text_H1
    }

    @DimenRes
    fun getLineHeight(@Typography typo: Int) = when (typo) {
        H1 -> R.dimen.h1_line_height
        H2 -> R.dimen.h2_line_height
        H3 -> R.dimen.h3_line_height
        H3_1 -> R.dimen.h3_1_line_height
        H4 -> R.dimen.h4_line_height
        B1 -> R.dimen.b1_line_height
        B2 -> R.dimen.b2_line_height
        B2_1 -> R.dimen.b2_1_line_height
        Button -> R.dimen.button_line_height
        Subtitle1 -> R.dimen.subtitle1_line_height
        Subtitle2 -> R.dimen.subtitle2_line_height
        Subtitle3 -> R.dimen.subtitle3_line_height
        Subtitle4 -> R.dimen.subtitle4_line_height
        Caption1 -> R.dimen.caption1_line_height
        Caption1_1 -> R.dimen.caption1_1_line_height
        Caption2 -> R.dimen.caption2_line_height
        Caption3 -> R.dimen.caption3_line_height
        Caption4 -> R.dimen.caption4_line_height
        Caption5 -> R.dimen.caption5_line_height
        else -> R.dimen.h1_line_height
    }
}