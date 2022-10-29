package com.hardy.yongbyung.components

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.annotation.IntDef
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.LayoutPlainButtonBinding

class YongByungPlainButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: LayoutPlainButtonBinding by lazy {
        LayoutPlainButtonBinding.inflate(
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

    @ButtonSize
    var size: Int = MEDIUM
        set(value) {
            field = value
            setState()
        }

    var text: String = ""
        set(value) {
            field = value
            binding.text.text = this.text
            setState()
        }

    private fun setState() {
        setButtonColor()

        when (size) {
            // setIcon And Text Size
            SMALL -> {
            }

            MEDIUM -> {

            }

            LARGE -> {

            }
        }
    }

    private fun setButtonColor() {
        when {
            isDisabled -> {
                setAtomTint(R.color.white)
                setBgColor(R.color.G300)
            }
            isPressed -> {
                setAtomTint(R.color.white)
                setBgColor(R.color.P300)
            }
            else -> {
                setAtomTint(R.color.white)
                setBgColor(R.color.P500)
            }
        }
    }

    override fun performClick(): Boolean {
        if(isDisabled) return false
        return super.performClick()
    }

    private fun setAtomTint(color: Int) {
        val atomTint = context.getColor(color)
        binding.text.setTextColor(atomTint)
    }

    private fun setBgColor(color: Int) {
        val bgColor = context.getColor(color)
        setBackgroundColor(bgColor)
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

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(value = [SMALL, MEDIUM, LARGE])
    annotation class ButtonSize

    companion object {
        const val SMALL = 0
        const val MEDIUM = 1
        const val LARGE = 2

        @JvmStatic
        @BindingAdapter("isDisabled")
        fun setDisabled(plainButton: YongByungPlainButton, isDisabled: Boolean) {
            plainButton.isDisabled = isDisabled
        }

        @JvmStatic
        @BindingAdapter("size")
        fun setSize(plainButton: YongByungPlainButton, @ButtonSize size: Int) {
            plainButton.size = size
        }

        @JvmStatic
        @BindingAdapter("android:text")
        fun setText(plainButton: YongByungPlainButton, text: String) {
            plainButton.text = text
        }
    }
}