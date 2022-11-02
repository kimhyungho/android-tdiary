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

    fun dateToString(date: Date?, format: SimpleDateFormat): String? {
        if (date == null) return null
        return format.format(date)
    }

    fun dateToAgoString(date: Date?): String? {
        if (date == null) return null
        val currentCalendar = Calendar.getInstance()
        val currentDate = currentCalendar.time

        val pastCalendar = Calendar.getInstance()
        pastCalendar.time = date

        val diffTime = (currentDate.time - date.time) / 1000
        return if (diffTime < SEC) {
            "방금 전"
        } else if ((diffTime / SEC) < MIN) {
            "${(diffTime / SEC)}분 전";
        } else if (((diffTime / SEC) / MIN) < HOUR) {
            "${((diffTime / SEC) / MIN)}시간 전"
        } else if ((((diffTime / SEC) / MIN) / HOUR) < DAY) {
            "${(((diffTime / SEC) / MIN) / HOUR)}일 전"
        } else if (currentCalendar.get(Calendar.YEAR) == pastCalendar.get(Calendar.YEAR)) {
            dateToString(date, MONTH_DAY_FORMAT)
        } else {
            dateToString(date, YEAR_MONTH_DAY_FORMAT)
        }
    }
}