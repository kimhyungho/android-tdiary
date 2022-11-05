package com.hardy.yongbyung.ui.writemessage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentWriteMessageBinding
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import com.hardy.yongbyung.utils.bind
import com.hardy.yongbyung.utils.clicks
import com.hardy.yongbyung.utils.throttleFirst
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WriteMessageFragment :
    BaseViewModelFragment<FragmentWriteMessageBinding, WriteMessageViewModel>(
        R.layout.fragment_write_message
    ) {
    override val viewModel: WriteMessageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            toolbar.startButtonClickListener = View.OnClickListener {
                navController.popBackStack()
            }

            sendButton.clicks()
                .throttleFirst(1000L)
                .bind(lifecycleScope) {
                    viewModel?.onSendButtonClick()
                }
        }

        with(viewModel) {
            lifecycleScope.launchWhenStarted {
                sendSuccess.collect {
                    navController.popBackStack()
                }
            }
        }
    }
}