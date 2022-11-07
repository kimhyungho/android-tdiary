package com.hardy.yongbyung.ui.agreement

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentAgreementBinding
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import com.hardy.yongbyung.dialog.TermDialogFragment
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

            termsOfServiceText.setOnClickListener {
                val dialog = TermDialogFragment.newInstance(TermDialogFragment.TERMS_OF_SERVICE)
                dialog.show(childFragmentManager, TermDialogFragment.TAG)
            }
        }

        with(viewModel) {
            lifecycleScope.launchWhenStarted {
                error.collect {
                    Snackbar.make(viewDataBinding.rootLayout, it, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}