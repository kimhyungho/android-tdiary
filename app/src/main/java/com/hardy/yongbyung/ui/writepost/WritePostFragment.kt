package com.hardy.yongbyung.ui.writepost

import androidx.fragment.app.viewModels
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentWritePostBinding
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WritePostFragment : BaseViewModelFragment<FragmentWritePostBinding, WritePostViewModel>(
    R.layout.fragment_write_post
) {
    override val viewModel: WritePostViewModel by viewModels()
}