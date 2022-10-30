package com.hardy.yongbyung.components

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.adapters.ListenerUtil

abstract class YongByungBaseTextField(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    companion object {
        @JvmStatic
        @BindingAdapter("android:text")
        fun setText(textFiled: YongByungBaseTextField, text: String) {
            textFiled.setText(text, TextView.BufferType.EDITABLE)
        }

        @JvmStatic
        @BindingAdapter("isDisabled")
        fun setIsDisabled(textFiled: YongByungBaseTextField, isDisabled: Boolean) {
            textFiled.isDisabled = isDisabled
        }

        @JvmStatic
        @BindingAdapter("error")
        fun setError(textFiled: YongByungBaseTextField, isError: Boolean?) {
            textFiled.isError = isError
        }

        @JvmStatic
        @BindingAdapter("android:hint")
        fun setHint(textFiled: YongByungBaseTextField, hint: String) {
            textFiled.hint = hint
        }

        @JvmStatic
        @BindingAdapter("android:inputType")
        fun setInputType(textFiled: YongByungBaseTextField, inputType: Int) {
            textFiled.inputType = inputType
        }

        @JvmStatic
        @BindingAdapter("android:maxLength")
        fun setMaxLength(textFiled: YongByungBaseTextField, maxLength: Int) {
            textFiled.maxLength = maxLength
        }

        @JvmStatic
        @BindingAdapter(
            value = ["android:beforeTextChanged", "android:onTextChanged", "android:afterTextChanged", "android:textAttrChanged"],
            requireAll = false
        )
        fun setTextWatcher(
            textFiled: YongByungBaseTextField,
            before: BeforeTextChanged?,
            on: OnTextChanged?,
            after: AfterTextChanged?,
            textAttrChanged: InverseBindingListener?
        ) {
            val newValue: TextWatcher =
                object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                        before?.beforeTextChanged(s, start, count, after)
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        on?.onTextChanged(s, start, before, count)
                        textAttrChanged?.onChange()
                    }

                    override fun afterTextChanged(s: Editable?) {
                        after?.afterTextChanged(s)
                    }
                }

            val oldValue = ListenerUtil.trackListener(
                textFiled,
                newValue,
                androidx.databinding.library.baseAdapters.R.id.textWatcher
            )
            oldValue?.let {
                textFiled.removeTextChangedListener(it)
            }
            newValue.let {
                textFiled.addTextChangedListener(it)
            }
        }
    }


    init {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        inflateLayout(context)
        initAttributes(context, attrs)
        setCurrentState()
        setEditTextFocusedListener()
    }

    abstract fun inflateLayout(context: Context)

    abstract fun initAttributes(context: Context, attrs: AttributeSet?)

    var isDisabled: Boolean = false
        set(value) {
            field = value
            setCurrentState()
        }

    abstract var isError: Boolean?

    abstract var text: Editable

    abstract var hint: CharSequence

    abstract var inputType: Int

    abstract var maxLength: Int

    fun setCurrentState() {
        setTextColor()
        setBackground()
        changeEditTextEnabled()
        setIconTint()
    }

    abstract fun shake()

    abstract fun changeEditTextEnabled()

    abstract fun addTextChangedListener(watcher: TextWatcher)

    abstract fun removeTextChangedListener(watcher: TextWatcher)

    private fun setTextColor() {
        when {
            isDisabled -> {
                setDisabledTextColor()
            }
            else -> {
                setDefaultTextColor()
            }
        }
    }

    abstract fun setText(text: CharSequence, type: TextView.BufferType)

    abstract fun setDefaultTextColor()

    abstract fun setEditTextTextColor(color: Int)

    abstract fun setHintTextColor(color: Int)

    abstract fun setErrorTextColor(color: Int)

    abstract fun setDisabledTextColor()

    abstract fun setBackground()

    abstract fun setIconTint()

    interface AfterTextChanged {
        fun afterTextChanged(s: Editable?)
    }

    interface BeforeTextChanged {
        fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
    }

    interface OnTextChanged {
        fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
    }

    abstract fun setEditTextFocusedListener()

}