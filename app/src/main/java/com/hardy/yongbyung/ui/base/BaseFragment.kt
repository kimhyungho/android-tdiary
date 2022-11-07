package com.hardy.yongbyung.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

abstract class BaseFragment<VD : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : Fragment() {
    private var _viewDataBinding: VD? = null
    val viewDataBinding: VD
        get() = _viewDataBinding
            ?: throw IllegalArgumentException("viewDataBinding can not be null")

    protected val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<VD>(inflater, layoutResId, container, false)
            .also { viewDataBinding ->
                viewDataBinding.lifecycleOwner = viewLifecycleOwner
                this._viewDataBinding = viewDataBinding
            }.root
    }

    override fun onDestroyView() {
        _viewDataBinding = null
        super.onDestroyView()
    }
}