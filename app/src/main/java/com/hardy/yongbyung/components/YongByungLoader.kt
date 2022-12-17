package com.hardy.yongbyung.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieDrawable
import com.hardy.yongbyung.databinding.LayoutLoaderBinding

class YongByungLoader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: LayoutLoaderBinding by lazy {
        LayoutLoaderBinding.inflate(LayoutInflater.from(context), this, true)
    }

    var isLoading: Boolean = false
        set(value) {
            field = value
            setLoadingInfo()
        }

    private fun setLoadingInfo() {
        if (isLoading) {
            visibility = VISIBLE
        } else {
            visibility = GONE
        }
    }

    init {
        setLoadingInfo()
    }

    companion object {
        @JvmStatic
        @BindingAdapter("loading")
        fun setLoading(loader: YongByungLoader, isLoading: Boolean) {
            loader.isLoading = isLoading
        }
    }
}