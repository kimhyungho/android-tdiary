package com.hardy.yongbyung.ui.messagedetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hardy.yongbyung.R
import com.hardy.yongbyung.adapters.MessageListAdapter
import com.hardy.yongbyung.databinding.FragmentMessageDetailBinding
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

            toolbar.startButtonClickListener = View.OnClickListener {
                navController.popBackStack()
            }
        }

        with(viewModel) {
            lifecycleScope.launchWhenStarted {
                messages.collect { messages ->
                    messageListAdapter.submitList(messages)
                }
            }
        }
    }
}