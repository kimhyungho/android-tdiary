package com.hardy.yongbyung.dialog

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentReportDialogBinding
import com.hardy.yongbyung.ui.base.BaseViewModelDialogFragment
import com.hardy.yongbyung.utils.bind
import com.hardy.yongbyung.utils.clicks
import com.hardy.yongbyung.utils.throttleFirst
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportDialog : BaseViewModelDialogFragment<FragmentReportDialogBinding, ReportViewModel>(
    R.layout.fragment_report_dialog
) {
    private val postId: String by lazy { arguments?.getString(POST_ID_KEY)!! }

    override val viewModel: ReportViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            reportButton.clicks()
                .throttleFirst(1000L)
                .bind(lifecycleScope) {
                    viewModel?.reportPost(postId)
                }
        }

        with(viewModel) {
            lifecycleScope.launchWhenStarted {
                reportSuccess.collect {
                    if (it != null) {
                        dismiss()
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                error.collect {
                    Snackbar.make(viewDataBinding.rootLayout, it, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        fun newInstance(
            postId: String
        ): ReportDialog {
            val fragment = ReportDialog()
            return fragment.apply {
                arguments = bundleOf(
                    POST_ID_KEY to postId,
                )
            }
        }

        private const val POST_ID_KEY = "POST_ID_KEY"

        const val TAG = "ReportDialog"
    }
}