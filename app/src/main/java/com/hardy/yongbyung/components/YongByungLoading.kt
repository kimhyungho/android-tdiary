package com.hardy.yongbyung.components

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import com.hardy.yongbyung.databinding.LayoutLoadingBinding

class YongByungLoading @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(
    context, attrs, defStyleAttr
) {
    private val binding: LayoutLoadingBinding =
        LayoutLoadingBinding.inflate(LayoutInflater.from(context), this, true)

    var isLoading: Boolean = false
        set(value) {
            field = value
            setState()
        }

    private fun setState() {
        if (isLoading) {
            binding.root.visibility = VISIBLE
            binding.progress.playAnimation()
        } else {
            binding.progress.pauseAnimation()
            binding.root.visibility = GONE
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("isLoading")
        fun bindIsLoading(loading: YongByungLoading, isLoading: Boolean) {
            loading.isLoading = isLoading
        }
    }
}