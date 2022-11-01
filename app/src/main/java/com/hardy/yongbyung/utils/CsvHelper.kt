package com.hardy.yongbyung.utils

import com.hardy.yongbyung.BuildConfig
import com.opencsv.CSVReader
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class CsvHelper(
    private val fileInputStream: InputStream
) {
    fun readData(): List<Array<String>> {
        return try {
            val inputStreamReader = InputStreamReader(fileInputStream)
            val reader = BufferedReader(inputStreamReader)
            CSVReader(reader).use {
                it.readAll()
            }
        } catch (e: IOException) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
            listOf()
        }
    }
}