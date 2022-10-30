package com.hardy.yongbyung.dialog

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hardy.yongbyung.R
import com.hardy.yongbyung.adapters.VerticalCategoryListAdapter
import com.hardy.yongbyung.databinding.FragmentSelectCategoryDialogBinding
import com.hardy.yongbyung.model.CategoryUiModel
import com.hardy.yongbyung.ui.base.BaseViewModelBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectCategoryDialog :
    BaseViewModelBottomSheetDialogFragment<FragmentSelectCategoryDialogBinding, SelectCategoryViewModel>(
        R.layout.fragment_select_category_dialog
    ) {
    var listener: Listener? = null

    private val selectedCategory: String? by lazy { arguments?.getString(SELECTED_CATEGORY_KEY) }

    override val viewModel: SelectCategoryViewModel by viewModels()


    private val verticalCategoryListAdapter = VerticalCategoryListAdapter().apply {
        listener = object : VerticalCategoryListAdapter.Listener {
            override fun onItemClick(category: CategoryUiModel) {
                this@SelectCategoryDialog.listener?.onCategorySelected(category)
                dismiss()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedCategory?.let { viewModel.setSelectedCategory(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            categoryRecyclerView.adapter = verticalCategoryListAdapter
        }

        with(viewModel) {
            lifecycleScope.launch {
                categories.collect {
                    verticalCategoryListAdapter.submitList(it)
                }
            }
        }
    }

    interface Listener {
        fun onCategorySelected(category: CategoryUiModel)
    }

    companion object {
        fun newInstance(
            selectedCategory: String?,
            onCategorySelected: (CategoryUiModel) -> Unit
        ): SelectCategoryDialog {
            val fragment = SelectCategoryDialog()
            return fragment.apply {
                arguments = bundleOf(
                    SELECTED_CATEGORY_KEY to selectedCategory,
                    SELECT_CATEGORY_KEY to onCategorySelected
                )

                listener = object : Listener {
                    override fun onCategorySelected(category: CategoryUiModel) {
                        onCategorySelected.invoke(category)
                    }
                }
            }
        }

        private const val SELECTED_CATEGORY_KEY = "SELECTED_CATEGORY_KEY"
        private const val SELECT_CATEGORY_KEY = "SELECT_CATEGORY_KEY"

        const val TAG = "SelectCategoryDialog"
    }
}