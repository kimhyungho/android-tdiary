package com.hardy.yongbyung.ui.wirtepost

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentWritePostBinding
import com.hardy.yongbyung.dialog.SelectCategoryDialog
import com.hardy.yongbyung.dialog.SelectDateDialog
import com.hardy.yongbyung.dialog.SelectSidoDialog
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import com.hardy.yongbyung.utils.bind
import com.hardy.yongbyung.utils.clicks
import com.hardy.yongbyung.utils.throttleFirst
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WritePostFragment : BaseViewModelFragment<FragmentWritePostBinding, WritePostViewModel>(
    R.layout.fragment_write_post
) {
    override val viewModel: WritePostViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            toolbar.startButtonClickListener = View.OnClickListener {
                navController.popBackStack()
            }

            categorySpinner.setOnClickListener {
                val categoryName = viewModel?.category?.value?.name
                val dialog = SelectCategoryDialog.newInstance(
                    categoryName
                ) { category -> viewModel?.setSelectedCategory(category) }
                dialog.show(childFragmentManager, SelectCategoryDialog.TAG)
            }

            dateSpinner.setOnClickListener {
                val selectedDate = viewModel?.date?.value?.second
                val dialog = SelectDateDialog.newInstance(
                    selectedDate?.time,
                ) { date -> viewModel?.onDateSelected(date) }
                dialog.show(childFragmentManager, SelectDateDialog.TAG)
            }

            regionSpinner.setOnClickListener {
                val selectedMainRegion = viewModel?.selectedMainRegion?.value
                val selectedSubRegion = viewModel?.selectedSubRegion?.value
                val dialog = SelectSidoDialog.newInstance(
                    selectedMainRegion = selectedMainRegion,
                    selectedSubRegion = selectedSubRegion,
                ) { mainRegion, subRegion ->
                    viewModel?.onRegionSelected(mainRegion, subRegion)
                }
                dialog.show(childFragmentManager, SelectSidoDialog.TAG)
            }

            confirmButton.clicks()
                .throttleFirst(1000L)
                .bind(lifecycleScope) {
                    viewModel?.onWriteButtonClick()
                }
        }

        with(viewModel) {
            lifecycleScope.launchWhenStarted {
                error.collect {
                    Snackbar.make(viewDataBinding.rootLayout, it, Snackbar.LENGTH_SHORT).show()
                }
            }

            lifecycleScope.launchWhenStarted {
                showPostDetail.collect {
                    if (!it.isNullOrEmpty()) navController
                        .navigate(
                            WritePostFragmentDirections
                                .actionDestWritePostToDestPostDetail(it)
                        )
                }
            }
        }
    }
}