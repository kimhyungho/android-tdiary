package com.hardy.yongbyung.components

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.hardy.yongbyung.R
import com.hardy.yongbyung.extensions.dpToIntPx
import com.hardy.yongbyung.extensions.getDimenFloat
import com.hardy.yongbyung.foundation.Typo

class YongByungTextArea @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatEditText(
    context, attrs, defStyleAttr
) {

    var type: Int = LINE
        set(value) {
            field = value
            setBackground()
        }

    init {
        setTextInfo()
        setTextColor(ContextCompat.getColor(context, R.color.G900))
        setHintTextColor(ContextCompat.getColor(context, R.color.G200))
        setBackground()
        isFocusableInTouchMode = true
    }

    private fun setTextInfo() {
        setTextAppearance(Typo.getStyle(Typo.Subtitle4))

        includeFontPadding = false

        val fontLineHeight = paint.getFontMetrics(paint.fontMetrics)

        val figmaLineHeight = context.getDimenFloat(Typo.getLineHeight(Typo.Subtitle4))

        val lineSpacing = figmaLineHeight - fontLineHeight

        setLineSpacing(lineSpacing, 1f)
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        setBackground()
    }

    private fun setBackground() {
        val padding = context.dpToIntPx(PADDING)
        setPadding(padding, padding, padding, padding)

        when (type) {
            LINE -> {
                when {
                    isFocused -> setBackground(R.drawable.bg_border_g400_1dp_rounded_8dp)
                    else -> setBackground(R.drawable.bg_border_g200_1dp_rounded_8dp)
                }
            }
            TRANSPARENT -> {
                background = null
            }
        }
    }

    private fun setBackground(@DrawableRes drawable: Int) {
        setBackgroundResource(drawable)
    }

    companion object {
        const val PADDING = 16f

        const val LINE = 0
        const val TRANSPARENT = 1

        @JvmStatic
        @BindingAdapter("type")
        fun setDisabled(textField: YongByungTextArea, type: Int) {
            textField.type = type
        }
    }
}