package com.hardy.yongbyung.dialog

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentSendMessageDialogBinding
import com.hardy.yongbyung.ui.base.BaseViewModelBottomSheetDialogFragment
import com.hardy.yongbyung.ui.postdetail.PostDetailFragment
import com.hardy.yongbyung.ui.postdetail.PostDetailFragmentDirections
import com.hardy.yongbyung.utils.bind
import com.hardy.yongbyung.utils.clicks
import com.hardy.yongbyung.utils.throttleFirst
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SendMessageDialog(
) : BaseViewModelBottomSheetDialogFragment<FragmentSendMessageDialogBinding, SendMessageViewModel>(
    R.layout.fragment_send_message_dialog
) {
    private val receiverUid: String by lazy { arguments?.getString(RECEIVER_UID_KEY)!! }

    override val viewModel: SendMessageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            sendButton.clicks()
                .throttleFirst(1000L)
                .bind(lifecycleScope) {
                    viewModel?.sendMessage(receiverUid)
                }
        }

        with(viewModel) {
            lifecycleScope.launchWhenStarted {
                sendSuccess.collect {
                    if (parentFragment is PostDetailFragment) {
                        parentFragment?.findNavController()
                            ?.navigate(
                                PostDetailFragmentDirections.actionDestPostDetailToDestMessageDetail(
                                    receiverUid,
                                    it
                                )
                            )
                    }
                    dismiss()
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
            receiverUid: String,
        ): SendMessageDialog {
            val fragment = SendMessageDialog()
            return fragment.apply {
                arguments = bundleOf(
                    RECEIVER_UID_KEY to receiverUid,
                )
            }
        }

        const val RECEIVER_UID_KEY = "RECEIVER_UID_KEY"
        const val TAG = "SendMessageDialog"
    }
}