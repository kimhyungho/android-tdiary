package com.hardy.yongbyung.dialog

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentMessageRoomMenuDialogBinding
import com.hardy.yongbyung.ui.base.BaseViewModelBottomSheetDialogFragment
import com.hardy.yongbyung.utils.bind
import com.hardy.yongbyung.utils.clicks
import com.hardy.yongbyung.utils.throttleFirst
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageRoomMenuDialog :
    BaseViewModelBottomSheetDialogFragment<FragmentMessageRoomMenuDialogBinding, MessageRoomMenuViewModel>(
        R.layout.fragment_message_room_menu_dialog
    ) {
    private val messageRoomId: String by lazy { arguments?.getString(MESSAGE_ROOM_ID_KEY)!! }

    override val viewModel: MessageRoomMenuViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            deleteButton.clicks()
                .throttleFirst(1000L)
                .bind(lifecycleScope) {
                    val dialog = AlertDialog.newInstance(
                        contentText = "정말 삭제하시겠습니까?",
                        negativeText = "취소",
                        positiveText = "삭제",
                        onPositiveButtonClick = {
                            this@MessageRoomMenuDialog.viewModel.onDeleteButtonClick(messageRoomId)
                        }
                    )
                    dialog.show(childFragmentManager, AlertDialog.TAG)
                }
        }

        with(viewModel) {
            lifecycleScope.launchWhenStarted {
                deleteSuccess.collect {
                    dismiss()
                    parentFragment?.findNavController()?.popBackStack()
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
            chatRoomId: String,
        ): MessageRoomMenuDialog {
            val fragment = MessageRoomMenuDialog()
            return fragment.apply {
                arguments = bundleOf(
                    MESSAGE_ROOM_ID_KEY to chatRoomId
                )
            }
        }

        private const val MESSAGE_ROOM_ID_KEY = "MESSAGE_ROOM_ID_KEY"

        const val TAG = "MessageRoomMenuDialog"
    }
}