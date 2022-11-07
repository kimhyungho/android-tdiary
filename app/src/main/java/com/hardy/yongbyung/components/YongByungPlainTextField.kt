package com.hardy.yongbyung.components

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.LayoutPlainTextFieldBinding

class YongByungPlainTextField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : YongByungBaseTextField(
    context, attrs, defStyleAttr
) {

    private lateinit var binding: LayoutPlainTextFieldBinding

    override fun inflateLayout(context: Context) {
        binding = LayoutPlainTextFieldBinding.inflate(
            LayoutInflater.from(context), this, true
        )
    }

    @SuppressLint("CustomViewStyleable")
    override fun initAttributes(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray: TypedArray =
                context.obtainStyledAttributes(attrs, R.styleable.BaseTextField)

            binding.edittext.setText(
                (typedArray.getString(R.styleable.BaseTextField_android_text) ?: "")
            )
            hint = typedArray.getString(R.styleable.BaseTextField_android_hint) ?: ""
            inputType = typedArray.getInt(
                R.styleable.BaseTextField_android_inputType,
                InputType.TYPE_CLASS_TEXT
            )

            typedArray.recycle()
        }
    }

    override var isError: Boolean? = false
        set(value) {
            field = value
            if (value == true) shake()
            setCurrentState()
        }

    override var text: Editable
        get() = binding.edittext.text
        set(value) {
            binding.edittext.text = value
        }

    override var hint: CharSequence
        get() = binding.edittext.hint
        set(value) {
            binding.edittext.hint = value
        }
    override var inputType: Int
        get() = binding.edittext.inputType
        set(value) {
            binding.edittext.inputType = value
        }

    override var maxLength: Int
        get() = 0
        set(value) {
            binding.edittext.filters =
                arrayOf(InputFilter.LengthFilter(value))
        }

    override fun shake() {
        startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
    }

    override fun changeEditTextEnabled() {
        binding.edittext.isEnabled = !isDisabled
    }

    override fun addTextChangedListener(watcher: TextWatcher) {
        binding.edittext.addTextChangedListener(watcher)
    }

    override fun removeTextChangedListener(watcher: TextWatcher) {
        binding.edittext.removeTextChangedListener(watcher)
    }

    override fun setText(text: CharSequence, type: TextView.BufferType) {
        binding.edittext.setText(text, type)
    }

    override fun setDefaultTextColor() {
        setEditTextTextColor(R.color.G600)
        setHintTextColor(R.color.G200)
    }

    override fun setEditTextTextColor(color: Int) {
        binding.edittext.setTextColor(resources.getColor(color, null))
    }

    override fun setHintTextColor(color: Int) {
        binding.edittext.setHintTextColor(resources.getColor(color, null))
    }

    override fun setErrorTextColor(color: Int) {
        binding.edittext.setTextColor(resources.getColor(color, null))
    }

    override fun setDisabledTextColor() {
        setEditTextTextColor(R.color.G600)
        setHintTextColor(R.color.G200)
    }

    override fun setBackground() {
        when {
            isError == true -> binding.inputFiled.setBackgroundResource(R.drawable.bg_underline_r800_2dp)
            binding.edittext.isFocused -> binding.inputFiled.setBackgroundResource(R.drawable.bg_underline_g300_2dp)
            else -> binding.inputFiled.setBackgroundResource(R.drawable.bg_underline_g200_2dp)
        }
    }

    override fun setEditTextFocusedListener() {
        binding.edittext.setOnFocusChangeListener { _, _ ->
            setCurrentState()
        }
    }

    override fun setIconTint() {
        // plain text field don't have icon
    }
}