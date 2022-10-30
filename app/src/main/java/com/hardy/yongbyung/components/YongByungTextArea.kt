package com.hardy.yongbyung.components

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter
import com.hardy.yongbyung.R
import com.hardy.yongbyung.extensions.dpToIntPx
import com.hardy.yongbyung.extensions.getDimenFloat
import com.hardy.yongbyung.foundation.Typo
import com.hardy.yongbyung.foundation.Typography

class YongByungTextArea @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatEditText(
    context, attrs, defStyleAttr
) {
    init {
        intiView(context, attrs)
        setState()
        isFocusableInTouchMode = true
    }

    @Typography
    var typo: Int = Typo.H1
        set(@Typography typo) {
            field = typo
            setTextInfo()
        }

    var isDisabled = false
        set(value) {
            field = value
            setState()
        }

    @SuppressLint("CustomViewStyleable")
    private fun intiView(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.YongByungTextArea)

            typo = typedArray.getInteger(R.styleable.YongByungTextArea_typo, Typo.H1)
            setTextInfo()

            typedArray.recycle()
        } else {
            setTextInfo()
        }
    }

    private fun setTextInfo() {
        setTextAppearance(Typo.getStyle(typo))

        includeFontPadding = false

        val fontLineHeight = paint.getFontMetrics(paint.fontMetrics)

        val figmaLineHeight = context.getDimenFloat(Typo.getLineHeight(typo))

        val lineSpacing = figmaLineHeight - fontLineHeight

        setLineSpacing(lineSpacing, 1f)
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        setState()
    }

    private fun setState() {
        setBackground()
    }

    private fun setBackground() {
        val padding = context.dpToIntPx(PADDING)
        setPadding(padding, padding, padding, padding)

        when {
            isFocused -> setBackground(R.drawable.bg_border_g400_1dp_rounded_8dp)
            else -> setBackground(R.drawable.bg_border_g200_1dp_rounded_8dp)
        }
    }

    private fun setBackground(@DrawableRes drawable: Int) {
        setBackgroundResource(drawable)
    }

    companion object {
        const val PADDING = 16f

        @JvmStatic
        @BindingAdapter("isDisabled")
        fun setDisabled(textArea: YongByungTextArea, isDisabled: Boolean) {
            textArea.isDisabled = isDisabled
        }
    }
}