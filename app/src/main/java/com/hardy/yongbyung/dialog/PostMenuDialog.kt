package com.hardy.yongbyung.dialog

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentPostMenuDialogBinding
import com.hardy.yongbyung.ui.base.BaseViewModelBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostMenuDialog :
    BaseViewModelBottomSheetDialogFragment<FragmentPostMenuDialogBinding, PostMenuViewModel>(
        R.layout.fragment_post_menu_dialog
    ) {
    private val postId: String? by lazy { arguments?.getString(POST_ID_KEY) }

    override val viewModel: PostMenuViewModel by viewModels()

    companion object {
        fun newInstance(
            postId: String,
        ): PostMenuDialog {
            val fragment = PostMenuDialog()
            return fragment.apply {
                arguments = bundleOf(
                    POST_ID_KEY to postId
                )
            }
        }

        private const val POST_ID_KEY = "POST_ID_KEY"

        const val TAG = "MessageRoomMenuDialog"
    }
}