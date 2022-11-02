package com.hardy.yongbyung.components

import android.content.Context
import android.graphics.Color
import android.graphics.PointF
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.hardy.yongbyung.databinding.LayoutIconCheckboxBinding
import com.hardy.yongbyung.foundation.Icon
import kotlin.math.abs

class YongByungIconCheckBox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: LayoutIconCheckboxBinding =
        LayoutIconCheckboxBinding.inflate(LayoutInflater.from(context), this, true)

    var defaultIcon: Int = Icon.ic_heart
        set(value) {
            field = value
            setState()
        }

    var defaultIconColor: Int = Color.BLACK
        set(value) {
            field = value
            setState()
        }

    var selectedIcon: Int = Icon.ic_heart_filled
        set(value) {
            field = value
            setState()
        }

    var selectedIconColor: Int = Color.BLACK
        set(value) {
            field = value
            setState()
        }

    var size: Int
        get() = binding.icon.size
        set(value) {
            binding.icon.size = value
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
    }

    private fun setDrawable() {
        when (isSelected) {
            true -> {
                changeDrawable(selectedIcon)
                changeDrawableColor(selectedIconColor)
            }
            false -> {
                changeDrawable(defaultIcon)
                changeDrawableColor(defaultIconColor)
            }
        }
    }

    private fun changeDrawable(value: Int) {
        binding.icon.setIconResource(value)
    }

    private fun changeDrawableColor(color: Int) {
        binding.icon.setColorFilter(color)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchPoint.set(event.x, event.y)
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
        isSelected = !isSelected
        setState()
    }

    override fun performClick(): Boolean {
        super.performClick()
        toggle()
        return true
    }

    companion object {
        @JvmStatic
        @BindingAdapter("isSelected")
        fun setSelected(checkBox: YongByungIconCheckBox, isSelected: Boolean) {
            checkBox.isSelected = isSelected
        }

        @JvmStatic
        @BindingAdapter("selectedIcon")
        fun setSelectedIcon(checkBox: YongByungIconCheckBox, icon: Int) {
            checkBox.selectedIcon = icon
        }

        @JvmStatic
        @BindingAdapter("selectedIconColor")
        fun setSelectedIconColor(checkBox: YongByungIconCheckBox, color: Int) {
            checkBox.selectedIconColor = color
        }

        @JvmStatic
        @BindingAdapter("defaultIcon")
        fun setDefaultIcon(checkBox: YongByungIconCheckBox, icon: Int) {
            checkBox.defaultIcon = icon
        }

        @JvmStatic
        @BindingAdapter("defaultIconColor")
        fun setDefaultIconColor(checkBox: YongByungIconCheckBox, color: Int) {
            checkBox.defaultIconColor = color
        }

        @JvmStatic
        @BindingAdapter("size")
        fun setSize(checkBox: YongByungIconCheckBox, size: Int) {
            checkBox.size = size
        }

        @JvmStatic
        @BindingAdapter("selectedListener")
        fun setSelectedListener(checkbox: YongByungIconCheckBox, listener: SelectedListener) {
            checkbox.setOnSelectedListener(listener)
        }
    }
}