package com.hardy.yongbyung.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.hardy.yongbyung.BR

abstract class BaseViewModelFragment<VD : ViewDataBinding, VM : ViewModel>(
    @LayoutRes private val layoutResId: Int
) : Fragment() {
    private var _viewDataBinding: VD? = null
    val viewDataBinding: VD
        get() = _viewDataBinding
            ?: throw IllegalArgumentException("viewDataBinding can not be null")

    abstract val viewModel: VM

    protected val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<VD>(inflater, layoutResId, container, false)
            .also { viewDataBinding ->
                viewDataBinding.lifecycleOwner = viewLifecycleOwner
                viewDataBinding.setVariable(BR.viewModel, viewModel)
                this._viewDataBinding = viewDataBinding
            }.root
    }

    override fun onDestroyView() {
        _viewDataBinding = null
        super.onDestroyView()
    }
}