package com.hardy.yongbyung.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import com.hardy.yongbyung.R
import com.hardy.yongbyung.adapters.HorizontalCategoryListAdapter
import com.hardy.yongbyung.adapters.PagingStateAdapter
import com.hardy.yongbyung.adapters.PostListAdapter
import com.hardy.yongbyung.databinding.FragmentHomeBinding
import com.hardy.yongbyung.dialog.ReportDialog
import com.hardy.yongbyung.dialog.SelectSidoDialog
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import com.hardy.yongbyung.ui.main.GatewayFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseViewModelFragment<FragmentHomeBinding, HomeViewModel>(
    R.layout.fragment_home
) {
    override val viewModel: HomeViewModel by viewModels()

    private val horizontalCategoryListAdapter = HorizontalCategoryListAdapter().apply {
        listener = object : HorizontalCategoryListAdapter.Listener {
            override fun onCategoryImageClick(name: String) {
                viewModel.onCategorySelect(name)
            }
        }
    }
    private val postListAdapter = PostListAdapter().apply {
        listener = object : PostListAdapter.Listener {
            override fun onItemClick(id: String) {
                val mainNavController = parentFragment?.parentFragment?.findNavController()
                mainNavController?.navigate(
                    GatewayFragmentDirections.actionDestMainToDestPostDetail(
                        id
                    )
                )
            }

            override fun onSirenClick(id: String) {
                val dialog = ReportDialog.newInstance(id)
                dialog.show(childFragmentManager, ReportDialog.TAG)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            lifecycleScope.launch {
                postListAdapter.loadStateFlow.collectLatest { loadStates ->
                    viewModel?.setPostLoading(loadStates.refresh is LoadState.Loading)
                    if (loadStates.refresh is LoadState.Error) {
                        viewModel?.setError((loadStates.refresh as LoadState.Error).error)
                    }

                    if (loadStates.append is LoadState.NotLoading && loadStates.append.endOfPaginationReached) {
                        viewModel?.setShowEmptyImage(postListAdapter.itemCount < 1)
                    }
                }
            }

            with(sportsRecyclerView) {
                itemAnimator = null
                adapter = horizontalCategoryListAdapter
            }

            with(postsRecyclerView) {
                itemAnimator = null
                adapter = postListAdapter.withLoadStateFooter(
                    footer = PagingStateAdapter { postListAdapter.retry() }
                )
            }

            writePostFab.setOnClickListener {
                val mainNavController = parentFragment?.parentFragment?.findNavController()
                mainNavController?.navigate(GatewayFragmentDirections.actionDestMainToDestWritePost())
            }

            regionFilterButton.setOnClickListener {
                val mainRegion = viewModel?.selectedMainRegion?.value
                val subRegion = viewModel?.selectedSubRegion?.value
                val dialog = SelectSidoDialog.newInstance(
                    mainRegion,
                    subRegion
                ) { selectedMainRegion, selectedSubRegion ->
                    viewModel?.onFilterSelected(selectedMainRegion, selectedSubRegion)
                }
                dialog.show(childFragmentManager, SelectSidoDialog.TAG)
            }
        }

        with(viewModel) {
            lifecycleScope.launchWhenStarted {
                categories.collect { category ->
                    horizontalCategoryListAdapter.submitList(category)
                }
            }

            lifecycleScope.launchWhenStarted {
                posts.collect { posts ->
                    postListAdapter.submitData(lifecycle, posts)
                }
            }

            lifecycleScope.launchWhenStarted {
                refreshing.collect {
                    viewDataBinding.postRefreshLayout.isRefreshing = it
                    if (it) {
                        postListAdapter.refresh()
                        viewModel.setRefreshing(false)
                    }
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
        with(viewDataBinding) {
            sportsRecyclerView.adapter = null
            postsRecyclerView.adapter = null
        }
        super.onDestroyView()
    }
}