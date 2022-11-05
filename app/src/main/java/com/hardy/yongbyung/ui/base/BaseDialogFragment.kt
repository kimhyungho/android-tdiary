package com.hardy.yongbyung.ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.hardy.yongbyung.R

abstract class BaseDialogFragment<VD : ViewDataBinding>(
    @LayoutRes
    private val layoutResId: Int
) : DialogFragment() {
    private var _viewDataBinding: VD? = null
    protected val viewDataBinding: VD
        get() = _viewDataBinding
            ?: throw IllegalArgumentException("viewDataBinding can not be null")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NO_TITLE, R.style.Theme_YongByung_Dialog)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DataBindingUtil.inflate<VD>(inflater, layoutResId, container, false)
            .also { viewDataBinding ->
                viewDataBinding.lifecycleOwner = this
                this._viewDataBinding = viewDataBinding
            }.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewDataBinding = null
    }
}