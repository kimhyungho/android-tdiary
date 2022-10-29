package com.hardy.yongbyung.components

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.hardy.yongbyung.R
import com.hardy.yongbyung.extensions.getDimenFloat
import com.hardy.yongbyung.foundation.Typo
import com.hardy.yongbyung.foundation.Typography

class YongByungText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(
    context, attrs, defStyleAttr
) {
    init {
        intiView(context, attrs)
    }

    @Typography
    var typo: Int = Typo.H1
        set(@Typography typo) {
            field = typo
            setTextInfo()
        }

    private fun intiView(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.YongByungText)

            typo = typedArray.getInteger(R.styleable.YongByungText_typo, Typo.H1)
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

        setPadding(0, lineSpacing.toInt() / 2, 0, lineSpacing.toInt() / 2)

        setLineSpacing(lineSpacing, 1f)
    }
}