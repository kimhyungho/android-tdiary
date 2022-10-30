package com.hardy.yongbyung.ui.wirtepost

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentWritePostBinding
import com.hardy.yongbyung.dialog.SelectCategoryDialog
import com.hardy.yongbyung.dialog.SelectDateDialog
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
                val dialog = SelectDateDialog.newInstance(

                )
                dialog.show(childFragmentManager, SelectDateDialog.TAG)
            }
        }

        with(viewModel) {

        }
    }
}