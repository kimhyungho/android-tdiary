package com.hardy.yongbyung.ui.messagedetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.hardy.yongbyung.R
import com.hardy.yongbyung.adapters.MessageListAdapter
import com.hardy.yongbyung.databinding.FragmentMessageDetailBinding
import com.hardy.yongbyung.dialog.MessageRoomMenuDialog
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageDetailFragment :
    BaseViewModelFragment<FragmentMessageDetailBinding, MessageDetailViewModel>(
        R.layout.fragment_message_detail
    ) {
    override val viewModel: MessageDetailViewModel by viewModels()

    private val messageListAdapter = MessageListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            messageRecyclerView.adapter = messageListAdapter

            with(toolbar) {
                startButtonClickListener = View.OnClickListener {
                    navController.popBackStack()
                }

                endFirstButtonClickListener = View.OnClickListener {
                    viewModel?.messageRoomUid?.let { messageRoomUid ->
                        val dialog = MessageRoomMenuDialog.newInstance(messageRoomUid)
                        dialog.show(childFragmentManager, MessageRoomMenuDialog.TAG)
                    }
                }

                endSecondButtonClickListener = View.OnClickListener {
                    viewModel?.opponentUid?.let {
                        navController.navigate(
                            MessageDetailFragmentDirections.actionDestMessageDetailToDestWriteMessage(
                                it
                            )
                        )
                    }
                }
            }
        }

        with(viewModel) {
            lifecycleScope.launchWhenStarted {
                messages.collect { messages ->
                    messageListAdapter.submitList(messages)
                }
            }

            lifecycleScope.launchWhenStarted {
                error.collect {
                    Snackbar.make(viewDataBinding.rootLayout, it, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}