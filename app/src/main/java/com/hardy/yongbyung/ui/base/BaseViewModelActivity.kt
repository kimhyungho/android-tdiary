package com.hardy.yongbyung.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.hardy.yongbyung.BR

abstract class BaseViewModelActivity<VD : ViewDataBinding, VM : ViewModel>(
    @LayoutRes private val layoutResId: Int
) : AppCompatActivity() {
    lateinit var viewDataBinding: VD
    abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        DataBindingUtil.setContentView<VD>(this, layoutResId).also { viewDataBinding ->
            viewDataBinding.lifecycleOwner = this
            viewDataBinding.setVariable(BR.viewModel, viewModel)
            this.viewDataBinding = viewDataBinding
        }
    }
}