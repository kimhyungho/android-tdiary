package com.hardy.yongbyung.ui.postdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentPostDetailBinding
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailFragment: BaseViewModelFragment<FragmentPostDetailBinding, PostDetailViewModel>(
    R.layout.fragment_post_detail
) {
    override val viewModel: PostDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}