package com.hardy.yongbyung.ui.agreement

import androidx.fragment.app.viewModels
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentAgreementBinding
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgreementFragment : BaseViewModelFragment<FragmentAgreementBinding, AgreementViewModel>(
    R.layout.fragment_agreement
) {
    override val viewModel: AgreementViewModel by viewModels()
}