package com.hardy.yongbyung.provider.resource

import android.content.Context
import androidx.annotation.RawRes
import com.hardy.yongbyung.provider.resource.ResourceProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStream
import javax.inject.Inject

class ResourceProviderImpl @Inject constructor(
    @ApplicationContext
    private val context: Context
) : ResourceProvider {
    private val resources = context.resources

    override fun getRawInputStream(@RawRes rawResId: Int): InputStream {
        return resources.openRawResource(rawResId)
    }
}