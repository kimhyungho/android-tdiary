package com.hardy.yongbyung.provider

import androidx.annotation.RawRes
import java.io.InputStream

interface ResourceProvider {
    fun getRawInputStream(@RawRes rawResId: Int): InputStream
}