package com.hardy.yongbyung.ui.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hardy.yongbyung.R

abstract class BaseBottomSheetDialogFragment<VD : ViewDataBinding>(
    @LayoutRes
    private val layoutResId: Int
) : BottomSheetDialogFragment() {
    private var _viewDataBinding: VD? = null
    protected val viewDataBinding: VD
        get() = _viewDataBinding
            ?: throw IllegalArgumentException("viewDataBinding can not be null")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NO_TITLE, R.style.Theme_YongByung_BottomSheetDialog)

        val dialog = super.onCreateDialog(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setWhiteNavigationBar(dialog)
        }

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<VD>(inflater, layoutResId, container, false)
            .also { viewDataBinding ->
                viewDataBinding.lifecycleOwner = viewLifecycleOwner
                this._viewDataBinding = viewDataBinding
            }?.root
    }

    private fun setWhiteNavigationBar(dialog: Dialog) {
        val window = dialog.window
        if (window != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val metrics = requireActivity().windowManager.currentWindowMetrics

                val dimDrawable = ColorDrawable(Color.TRANSPARENT)
                val navigationBarDrawable = ColorDrawable(Color.WHITE)

                val layers = arrayOf(dimDrawable, navigationBarDrawable)
                val windowBackground = LayerDrawable(layers)
                windowBackground.setLayerInsetTop(1, metrics.bounds.height())

                window.setBackgroundDrawable(windowBackground)
            } else {
                val metrics = DisplayMetrics()

                window.windowManager.defaultDisplay.getMetrics(metrics)

                val dimDrawable = ColorDrawable(Color.TRANSPARENT)
                val navigationBarDrawable = ColorDrawable(Color.WHITE)

                val layers = arrayOf(dimDrawable, navigationBarDrawable)
                val windowBackground = LayerDrawable(layers)
                windowBackground.setLayerInsetTop(1, metrics.heightPixels)

                window.setBackgroundDrawable(windowBackground)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewDataBinding = null
    }
}