package com.hardy.yongbyung.components

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.LayoutBoxButtonBinding
import com.hardy.yongbyung.extensions.dpToIntPx

class YongByungBoxButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: LayoutBoxButtonBinding by lazy {
        LayoutBoxButtonBinding.inflate(
            LayoutInflater.from(
                context
            ), this, true
        )
    }

    var isDisabled: Boolean = false
        set(value) {
            field = value
            setState()
        }

    var size: Int = MEDIUM
        set(value) {
            field = value
            setState()
        }

    var type: Int = FILLED
        set(value) {
            field = value
            setState()
        }

    var rounding: Int = 0
        set(value) {
            field = value
            setState()
        }

    var text: String = ""
        set(value) {
            field = value
            setState()
        }

    @ColorRes
    private var itemColor: Int = 0

    @ColorRes
    private var bgColor: Int = 0


    private fun setState() {
        setTheme()
        setText()
        requestLayout()
        invalidate()
    }

    private fun setTheme() {
        setSize()
        when (type) {
            FILLED -> setFilledTheme()
            LINE -> setLineTheme()
        }
        setColorAndStroke()
    }

    private fun setFilledTheme() {
        when {
            isDisabled -> {
                itemColor = R.color.white
                bgColor = R.color.G300
            }
            isPressed -> {
                itemColor = R.color.white
                bgColor = R.color.P300
            }
            else -> {
                itemColor = R.color.white
                bgColor = R.color.P500
            }
        }
    }

    private fun setLineTheme() {
        when {
            isDisabled -> {
                itemColor = R.color.G300
                bgColor = R.color.G300
            }
            isPressed -> {
                itemColor = R.color.P300
                bgColor = R.color.P300
            }
            else -> {
                itemColor = R.color.P500
                bgColor = R.color.P500
            }
        }
    }

    private fun setColorAndStroke() {
        val drawable = if (rounding == 24) {
            ContextCompat.getDrawable(
                context,
                R.drawable.box_button_background_24dp
            ) as GradientDrawable
        } else {
            ContextCompat.getDrawable(
                context,
                R.drawable.box_button_background_4dp
            ) as GradientDrawable
        }

        binding.text.setTextColor(ContextCompat.getColor(context, itemColor))

        drawable.setColor(
            if (type == LINE) Color.TRANSPARENT else ContextCompat.getColor(
                context,
                bgColor
            )
        )

        drawable.setStroke(
            context.dpToIntPx(if (type == LINE) 1F else 0F),
            ContextCompat.getColor(context, itemColor)
        )

        binding.boxButtonFrame.background = drawable
    }

    private fun setSize() {
        val layoutParams = binding.boxButtonFrame.layoutParams as LayoutParams

        when (size) {
            SMALL -> {
                layoutParams.height = context.dpToIntPx(32F)
                binding.boxButtonFrame.setPadding(
                    context.dpToIntPx(12F),
                    0,
                    context.dpToIntPx(12F),
                    0
                )
            }

            MEDIUM -> {
                layoutParams.height = context.dpToIntPx(40F)
                binding.boxButtonFrame.setPadding(
                    context.dpToIntPx(16F),
                    0,
                    context.dpToIntPx(16F),
                    0
                )
            }

            LARGE -> {
                layoutParams.height = context.dpToIntPx(48F)
                binding.boxButtonFrame.setPadding(
                    context.dpToIntPx(16F),
                    0,
                    context.dpToIntPx(16F),
                    0
                )
            }
        }
    }

    private fun setText() {
        binding.text.text = this.text
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        if (event != null) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    isPressed = true
                    setState()
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    isPressed = false
                    setState()
                }
            }
        }
        return true
    }

    override fun performClick(): Boolean {
        if(isDisabled) return false
        return super.performClick()
    }

    companion object {
        const val FILLED = 0
        const val LINE = 1

        const val SMALL = 0
        const val MEDIUM = 1
        const val LARGE = 2

        @JvmStatic
        @BindingAdapter("isDisabled")
        fun setDisabled(boxButton: YongByungBoxButton, isDisabled: Boolean) {
            boxButton.isDisabled = isDisabled
        }

        @JvmStatic
        @BindingAdapter("size")
        fun setSize(boxButton: YongByungBoxButton, size: Int) {
            boxButton.size = size
        }

        @JvmStatic
        @BindingAdapter("type")
        fun setType(boxButton: YongByungBoxButton, type: Int) {
            boxButton.type = type
        }

        @JvmStatic
        @BindingAdapter("rounding")
        fun setRounding(boxButton: YongByungBoxButton, rounding: Int) {
            boxButton.rounding = rounding
        }

        @JvmStatic
        @BindingAdapter("android:text")
        fun setText(boxButton: YongByungBoxButton, text: String) {
            boxButton.text = text
        }
    }
}
