package com.hardy.yongbyung.ui.wirtepost

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentWritePostBinding
import com.hardy.yongbyung.dialog.SelectCategoryDialog
import com.hardy.yongbyung.dialog.SelectDateDialog
import com.hardy.yongbyung.dialog.SelectSidoDialog
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WritePostFragment : BaseViewModelFragment<FragmentWritePostBinding, WritePostViewModel>(
    R.layout.fragment_write_post
) {
    override val viewModel: WritePostViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
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

            confirmButton.setOnClickListener {
                viewModel?.onWriteButtonClick()
            }
        }

        with(viewModel) {
            lifecycleScope.launchWhenStarted {
            }
        }
    }
}