package com.hardy.yongbyung.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hardy.yongbyung.R
import com.hardy.yongbyung.adapters.HorizontalCategoryListAdapter
import com.hardy.yongbyung.databinding.FragmentHomeBinding
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import com.hardy.yongbyung.ui.main.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseViewModelFragment<FragmentHomeBinding, HomeViewModel>(
    R.layout.fragment_home
) {
    override val viewModel: HomeViewModel by viewModels()

    private val horizontalCategoryListAdapter = HorizontalCategoryListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            sportsRecyclerView.adapter = horizontalCategoryListAdapter

            writePostFab.setOnClickListener {
                val mainNavController = parentFragment?.parentFragment?.findNavController()
                mainNavController?.navigate(MainFragmentDirections.actionDestMainToDestWritePost())
            }
        }

        with(viewModel) {
            lifecycleScope.launch {
                categories.collect { category ->
                    horizontalCategoryListAdapter.submitList(category)
                }
            }
        }
    }
}