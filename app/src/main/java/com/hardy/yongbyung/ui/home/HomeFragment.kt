package com.hardy.yongbyung.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hardy.yongbyung.R
import com.hardy.yongbyung.adapters.HorizontalCategoryListAdapter
import com.hardy.yongbyung.adapters.PostListAdapter
import com.hardy.yongbyung.databinding.FragmentHomeBinding
import com.hardy.yongbyung.dialog.SelectSidoDialog
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import com.hardy.yongbyung.ui.main.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
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
                mainNavController?.navigate(MainFragmentDirections.actionDestMainToDestPostDetail(id))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            with(sportsRecyclerView) {
                itemAnimator = null
                adapter = horizontalCategoryListAdapter
            }

            with(postsRecyclerView) {
                itemAnimator = null
                adapter = postListAdapter
            }

            writePostFab.setOnClickListener {
                val mainNavController = parentFragment?.parentFragment?.findNavController()
                mainNavController?.navigate(MainFragmentDirections.actionDestMainToDestWritePost())
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
        }
    }
}