package com.hardy.yongbyung.dialog

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentSendMessageDialogBinding
import com.hardy.yongbyung.ui.base.BaseViewModelBottomSheetDialogFragment

class SendMessageDialog(
) : BaseViewModelBottomSheetDialogFragment<FragmentSendMessageDialogBinding, SendMessageViewModel>(
    R.layout.fragment_message
) {
    private val receiverUid: String by lazy { arguments?.getString(RECEIVER_UID_KEY)!! }

    override val viewModel: SendMessageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
    }
}