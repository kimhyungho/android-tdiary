package com.hardy.yongbyung.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import com.hardy.yongbyung.R
import com.hardy.yongbyung.adapters.PostListAdapter
import com.hardy.yongbyung.databinding.FragmentProfileBinding
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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

        lifecycleScope.launch {
            postListAdapter.loadStateFlow.collectLatest { loadStates ->
                viewModel.setPostLoading(loadStates.refresh is LoadState.Loading)
                if (loadStates.refresh is LoadState.Error) {
                    viewModel.setError((loadStates.refresh as LoadState.Error).error)
                }

                if (loadStates.append is LoadState.NotLoading && loadStates.append.endOfPaginationReached) {
                    viewModel.setShowEmptyImage(postListAdapter.itemCount < 1)
                }
            }
        }

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

            lifecycleScope.launchWhenStarted {
                error.collect {
                    Snackbar.make(viewDataBinding.rootLayout, it, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        viewDataBinding.postsRecyclerView.adapter = null
        super.onDestroyView()
    }
}