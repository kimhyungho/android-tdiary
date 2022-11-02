package com.hardy.yongbyung.ui.profile

import androidx.fragment.app.viewModels
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentProfileBinding
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseViewModelFragment<FragmentProfileBinding, ProfileViewModel>(
    R.layout.fragment_profile
) {
    override val viewModel: ProfileViewModel by viewModels()


}