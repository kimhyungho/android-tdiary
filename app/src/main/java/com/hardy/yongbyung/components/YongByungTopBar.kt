package com.hardy.yongbyung.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.hardy.yongbyung.databinding.LayoutTopBarBinding
import com.hardy.yongbyung.foundation.Icon

class YongByungTopBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: LayoutTopBarBinding =
        LayoutTopBarBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = null
        set(title) {
            field = title
            setTitleText()
        }

    var startButtonClickListener: OnClickListener? = null
        set(value) {
            binding.startButton.setOnClickListener(value)
        }

    var endFirstButtonClickListener: OnClickListener? = null
        set(value) {
            binding.endFirstButton.setOnClickListener(value)
        }

    var endSecondButtonClickListener: OnClickListener? = null
        set(value) {
            binding.endSecondButton.setOnClickListener(value)
        }

    @Icon.Iconography
    var startIcon: Int? = null
        set(icon) {
            field = icon
            binding.startButton.icon = icon!!
        }

    @Icon.Iconography
    var endFirstIcon: Int? = null
        set(icon) {
            field = icon
            binding.endFirstButton.icon = icon!!
        }

    @Icon.Iconography
    var endSecondIcon: Int? = null
        set(icon) {
            field = icon
            binding.endSecondButton.icon = icon!!
        }

    var hasDivider: Boolean = false
        set(hasDivider) {
            field = hasDivider
            if (hasDivider) binding.divider.visibility = View.VISIBLE
            else binding.divider.visibility = View.GONE
        }

    private fun setTitleText() {
        binding.title.text = title
    }

    companion object {
        @JvmStatic
        @BindingAdapter("startIcon")
        fun setStartIcon(topBar: YongByungTopBar, @Icon.Iconography icon: Int) {
            topBar.startIcon = icon
        }

        @JvmStatic
        @BindingAdapter("endFirstIcon")
        fun setEndFirstIcon(topBar: YongByungTopBar, @Icon.Iconography icon: Int) {
            topBar.endFirstIcon = icon
        }

        @JvmStatic
        @BindingAdapter("endSecondIcon")
        fun setEndSecondIcon(topBar: YongByungTopBar, @Icon.Iconography icon: Int) {
            topBar.endSecondIcon = icon
        }

        @JvmStatic
        @BindingAdapter("title")
        fun setTitle(topBar: YongByungTopBar, title: String?) {
            topBar.title = title
        }

        @JvmStatic
        @BindingAdapter("onStartIconClick")
        fun setStartIconClickListener(topBar: YongByungTopBar, onClickListener: OnClickListener) {
            topBar.startButtonClickListener = onClickListener
        }

        @JvmStatic
        @BindingAdapter("onEndFirstClick")
        fun setOnEndFirstClickListener(
            topBar: YongByungTopBar,
            endFirstClickListener: OnClickListener
        ) {
            topBar.endFirstButtonClickListener = endFirstClickListener
        }

        @JvmStatic
        @BindingAdapter("onEndSecondIconClick")
        fun setOnEndSecondClickListener(
            topBar: YongByungTopBar,
            endSecondClickListener: OnClickListener
        ) {
            topBar.endSecondButtonClickListener = endSecondClickListener
        }

        @JvmStatic
        @BindingAdapter("hasDivider")
        fun setDivider(
            topBar: YongByungTopBar,
            hasDivider: Boolean
        ) {
            topBar.hasDivider = hasDivider
        }
    }
}