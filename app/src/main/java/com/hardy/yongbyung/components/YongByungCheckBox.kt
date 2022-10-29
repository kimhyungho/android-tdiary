package com.hardy.yongbyung.components

import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.LayoutCheckBoxBinding
import com.hardy.yongbyung.extensions.dpToIntPx
import com.hardy.yongbyung.foundation.Icon
import com.hardy.yongbyung.foundation.Typo
import kotlin.math.abs

class YongByungCheckBox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: LayoutCheckBoxBinding =
        LayoutCheckBoxBinding.inflate(LayoutInflater.from(context), this, true)

    var size: Int = SMALL
        set(size) {
            field = size
            setSizeState()
        }

    var label: String = ""
        set(label) {
            field = label
            binding.text.text = label
        }

    var isDisabled: Boolean = false
        set(isDisabled) {
            field = isDisabled
            setState()
        }

    init {
        setState()
    }

    interface SelectedListener {
        fun onSelected(boolean: Boolean)
    }

    private var selectedListener: SelectedListener? = null

    fun setOnSelectedListener(listener: SelectedListener) {
        selectedListener = listener
    }

    override fun setSelected(selected: Boolean) {
        if (isSelected != selected) {
            super.setSelected(selected)
            selectedListener?.onSelected(selected)
            setState()
        }
    }

    private val touchPoint = PointF(0f, 0f)

    private fun setState() {
        setDrawable()
        setTotalColor()
        setSizeState()
    }

    private fun setTotalColor() {
        when (isDisabled) {
            true -> {
                changeTextColor(R.color.G300)
                changeDrawableColor(R.color.G300)
            }
            false -> if (isSelected) {
                changeTextColor(R.color.G500)
                changeDrawableColor(R.color.P500)
            } else {
                changeTextColor(R.color.G500)
                changeDrawableColor(R.color.G300)
            }
        }
    }

    private fun setDrawable() {
        when (isSelected) {
            true -> changeDrawable(Icon.ic_check_filled)
            false -> changeDrawable(Icon.ic_check)
        }
    }

    private fun changeDrawable(value: Int) {
        binding.icon.setIconResource(value)
    }

    private fun setSizeState() {
        when (size) {
            LARGE -> changeTotalSize(Typo.Button, MARGIN_LARGE, YongByungIcon.Large)
            MEDIUM -> changeTotalSize(Typo.Button, MARGIN_MEDIUM, YongByungIcon.Medium)
            else -> changeTotalSize(Typo.Button, MARGIN_SMALL, YongByungIcon.Small)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!isDisabled) {
                    touchPoint.set(event.x, event.y)
                }
            }
            MotionEvent.ACTION_UP -> {
                if (abs(touchPoint.x - event.x) < width.toFloat()
                    && abs(touchPoint.y - event.y) < height.toFloat()
                ) {
                    performClick()
                }
            }
        }

        return true
    }

    private fun toggle() {
        if (!isDisabled) {
            isSelected = !isSelected
            setState()
        }
    }

    override fun performClick(): Boolean {
        super.performClick()
        toggle()
        return true
    }

    companion object {
        const val SMALL = 1
        const val MEDIUM = 2
        const val LARGE = 3
        private const val MARGIN_SMALL = 4f
        private const val MARGIN_MEDIUM = 8f
        private const val MARGIN_LARGE = 8f

        @JvmStatic
        @BindingAdapter("isDisabled")
        fun setDisabled(checkBox: YongByungCheckBox, isDisabled: Boolean) {
            checkBox.isDisabled = isDisabled
        }

        @JvmStatic
        @BindingAdapter("isSelected")
        fun setSelected(checkBox: YongByungCheckBox, isSelected: Boolean) {
            checkBox.isSelected = isSelected
        }

        @JvmStatic
        @BindingAdapter("label")
        fun setText(checkBox: YongByungCheckBox, label: String) {
            checkBox.label = label
        }

        @JvmStatic
        @BindingAdapter("size")
        fun setSize(checkBox: YongByungCheckBox, size: Int) {
            checkBox.size = size
        }

        @JvmStatic
        @BindingAdapter("selectedListener")
        fun setSelectedListener(checkbox: YongByungCheckBox, listener: SelectedListener) {
            checkbox.setOnSelectedListener(listener)
        }
    }

    private fun changeTotalSize(typo: Int, marginSize: Float, imageSize: Int) {
        changeTypo(typo)
        changeMarginLeft(marginSize)
        changeImageSize(imageSize)
    }

    private fun changeTextColor(colorId: Int) {
        binding.text.setTextColor(ContextCompat.getColor(context, colorId))
    }

    private fun changeDrawableColor(colorId: Int) {
        binding.icon.setColorFilter(ContextCompat.getColor(context, colorId))
    }

    private fun changeTypo(typo: Int) {
        binding.text.typo = typo
    }

    private fun changeImageSize(value: Int) {
        binding.icon.setIconViewSize(value)
    }

    private fun changeMarginLeft(value: Float) {
        (binding.text.layoutParams as LayoutParams).setMargins(
            context.dpToIntPx(value),
            0,
            0,
            0
        )
    }
}
