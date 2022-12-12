package com.hardy.yongbyung.ui.community

import androidx.fragment.app.viewModels
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentCommunityBinding
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CommunityFragment @Inject constructor(): BaseViewModelFragment<FragmentCommunityBinding, CommunityViewModel>(
    R.layout.fragment_community
) {
    override val viewModel: CommunityViewModel by viewModels()
}