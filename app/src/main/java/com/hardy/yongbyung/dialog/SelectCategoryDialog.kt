package com.hardy.yongbyung.dialog

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentSelectCategoryDialogBinding
import com.hardy.yongbyung.ui.base.BaseViewModelBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectCategoryDialog :
    BaseViewModelBottomSheetDialogFragment<FragmentSelectCategoryDialogBinding, SelectCategoryViewModel>(
        R.layout.fragment_select_category_dialog
    ) {
    var listener: Listener? = null

    override val viewModel: SelectCategoryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {

        }
    }

    interface Listener {
        fun onCategorySelected()
    }

    companion object {
        fun newInstance(
            onCategorySelected: () -> Unit
        ): SelectCategoryDialog {
            val fragment = SelectCategoryDialog()
            return fragment.apply {
                arguments = bundleOf(
                    SELECT_CATEGORY_KEY to onCategorySelected
                )

                listener = object : Listener {
                    override fun onCategorySelected() {
                        onCategorySelected()
                    }
                }
            }
        }

        const val SELECT_CATEGORY_KEY = "SELECT_CATEGORY_KEY"

        const val TAG = "SelectCategoryDialog"
    }
}