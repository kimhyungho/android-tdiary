package com.hardy.yongbyung.ui.postdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentPostDetailBinding
import com.hardy.yongbyung.dialog.SendMessageDialog
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import com.hardy.yongbyung.utils.bind
import com.hardy.yongbyung.utils.clicks
import com.hardy.yongbyung.utils.throttleFirst
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailFragment : BaseViewModelFragment<FragmentPostDetailBinding, PostDetailViewModel>(
    R.layout.fragment_post_detail
) {
    override val viewModel: PostDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            profileImage.setOnClickListener {
                val uid = viewModel?.uid ?: return@setOnClickListener
                navController.navigate(
                    PostDetailFragmentDirections.actionDestPostDetailToDestProfile(uid)
                )
            }

            toolbar.startButtonClickListener = View.OnClickListener {
                navController.popBackStack()
            }

            sendMessageButton.clicks()
                .throttleFirst(1000L)
                .bind(lifecycleScope) {
                    if (viewModel?.isMyPost?.value == true) {
                        viewModel?.finishRecruiting()
                    } else {
                        val receiverUid = viewModel?.post?.value?.uid ?: return@bind
                        val dialog = SendMessageDialog.newInstance(receiverUid)
                        dialog.show(childFragmentManager, SendMessageDialog.TAG)
                    }
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