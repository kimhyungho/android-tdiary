package com.hardy.yongbyung.ui.agreement

import android.os.Bundle
import android.util.Log
import android.view.View
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            nextButton.setOnClickListener {
                viewModel?.onNextButtonClick()
            }
        }
    }
}