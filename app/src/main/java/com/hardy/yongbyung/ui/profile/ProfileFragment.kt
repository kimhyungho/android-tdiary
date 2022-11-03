package com.hardy.yongbyung.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hardy.yongbyung.R
import com.hardy.yongbyung.adapters.PostListAdapter
import com.hardy.yongbyung.databinding.FragmentProfileBinding
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseViewModelFragment<FragmentProfileBinding, ProfileViewModel>(
    R.layout.fragment_profile
) {
    override val viewModel: ProfileViewModel by viewModels()

    private val postListAdapter = PostListAdapter().apply {
        listener = object : PostListAdapter.Listener {
            override fun onItemClick(id: String) {
                navController
                    .navigate(ProfileFragmentDirections.actionDestProfileToDestPostDetail(id))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            with(postsRecyclerView) {
                itemAnimator = null
                adapter = postListAdapter
            }

            toolbar.startButtonClickListener = View.OnClickListener {
                navController.popBackStack()
            }
        }

        with(viewModel) {
            lifecycleScope.launchWhenStarted {
                posts.collect {
                    postListAdapter.submitData(lifecycle, it)
                }
            }
        }
    }
}