package com.hardy.yongbyung.ui.messagelist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hardy.yongbyung.R
import com.hardy.yongbyung.adapters.MessageRoomListAdapter
import com.hardy.yongbyung.databinding.FragmentMessageListBinding
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import com.hardy.yongbyung.ui.main.GatewayFragmentDirections
import com.hardy.yongbyung.ui.main.GatewayViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageListFragment : BaseViewModelFragment<FragmentMessageListBinding, GatewayViewModel>(
    R.layout.fragment_message_list
) {
    override val viewModel: GatewayViewModel by viewModels(ownerProducer = { requireParentFragment().requireParentFragment() })

    private val messageRoomListAdapter = MessageRoomListAdapter().apply {
        listener = object : MessageRoomListAdapter.Listener {
            override fun onItemClick(uid: String, messageRoomId: String) {
                val mainNavController = parentFragment?.parentFragment?.findNavController()
                mainNavController?.navigate(
                    GatewayFragmentDirections.actionDestMainToDestMessageDetail(uid, messageRoomId)
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            messageRoomRecyclerView.adapter = messageRoomListAdapter
        }

        with(viewModel) {
            lifecycleScope.launchWhenStarted {
                messageRooms.collect(messageRoomListAdapter::submitList)
            }

            lifecycleScope.launchWhenStarted {
                error.collect {
                    Snackbar.make(viewDataBinding.rootLayout, it, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        viewDataBinding.messageRoomRecyclerView.adapter = null
        super.onDestroyView()
    }
}