package com.hardy.yongbyung.utils

import android.annotation.SuppressLint
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@SuppressLint("SimpleDateFormat")
object DateUtil {
    val DOTTED_FORMAT: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd(E)")
    val MONTH_DAY_FORMAT: SimpleDateFormat = SimpleDateFormat("M월 d일")
    val YEAR_MONTH_DAY_FORMAT: SimpleDateFormat = SimpleDateFormat("yyyy년 M월 d일")

    private const val SEC = 60
    private const val MIN = 60
    private const val HOUR = 24
    private const val DAY = 7

    fun dateToString(date: Date?, format: SimpleDateFormat = DOTTED_FORMAT): String? {
        if (date == null) return null
        return format.format(date)
    }

    fun stringToDate(date: String?, format: SimpleDateFormat = DOTTED_FORMAT): Date? {
        if (date == null) return null
        return format.parse(date)
    }
}