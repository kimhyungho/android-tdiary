package com.hardy.yongbyung.dialog

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentPostMenuDialogBinding
import com.hardy.yongbyung.ui.base.BaseViewModelBottomSheetDialogFragment
import com.hardy.yongbyung.utils.bind
import com.hardy.yongbyung.utils.clicks
import com.hardy.yongbyung.utils.throttleFirst
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostMenuDialog :
    BaseViewModelBottomSheetDialogFragment<FragmentPostMenuDialogBinding, PostMenuViewModel>(
        R.layout.fragment_post_menu_dialog
    ) {
    private val postId: String by lazy { arguments?.getString(POST_ID_KEY)!! }
    private val uid: String by lazy { arguments?.getString(UID_KEY)!! }

    override val viewModel: PostMenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setIsMyPost(uid)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            reportButton.clicks()
                .throttleFirst(1000L)
                .bind(lifecycleScope) {
                    val dialog = ReportDialog.newInstance(postId)
                    dialog.show(childFragmentManager, ReportDialog.TAG)
                }

            deleteButton.clicks()
                .throttleFirst(1000L)
                .bind(lifecycleScope) {
                    val dialog = AlertDialog.newInstance(
                        getString(R.string.delete_check),
                        getString(R.string.cancel),
                        getString(R.string.delete),
                        onPositiveButtonClick = { viewModel?.deletePost(postId) }
                    )
                    dialog.show(childFragmentManager, AlertDialog.TAG)
                }
        }

        with(viewModel) {
            lifecycleScope.launchWhenStarted {
                successDelete.collect {
                    if (it != null) parentFragment?.findNavController()?.popBackStack()
                }
            }
        }
    }

    companion object {
        fun newInstance(
            postId: String,
            uid: String
        ): PostMenuDialog {
            val fragment = PostMenuDialog()
            return fragment.apply {
                arguments = bundleOf(
                    POST_ID_KEY to postId,
                    UID_KEY to uid
                )
            }
        }

        private const val POST_ID_KEY = "POST_ID_KEY"
        private const val UID_KEY = "UID_KEY"

        const val TAG = "PostMenuDialog"
    }
}