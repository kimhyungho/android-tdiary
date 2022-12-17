package com.hardy.yongbyung.dialog

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.hardy.domain.model.Place
import com.hardy.yongbyung.R
import com.hardy.yongbyung.adapters.PlaceListAdapter
import com.hardy.yongbyung.databinding.FragmentFindLocationDialogBinding
import com.hardy.yongbyung.model.PlaceUiModel
import com.hardy.yongbyung.ui.base.BaseViewModelBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindLocationDialog :
    BaseViewModelBottomSheetDialogFragment<FragmentFindLocationDialogBinding, FindLocationViewModel>(
        R.layout.fragment_find_location_dialog
    ) {
    override val viewModel: FindLocationViewModel by viewModels()

    var listener: Listener? = null

    private val placeListAdapter = PlaceListAdapter().apply {
        listener = object : PlaceListAdapter.Listener {
            override fun onPlaceCheck(item: PlaceUiModel) {
                viewModel.onPlaceSelect(item)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            with(placeRecyclerView) {
                adapter = placeListAdapter
                itemAnimator = null
            }

            confirmButton.setOnClickListener {
                val place = viewModel?.places?.value?.first { it.isSelected }?.toDomain()
                listener?.onConfirmButtonClick(place)
                dismiss()
            }
        }

        with(viewModel) {
            lifecycleScope.launchWhenCreated {
                places.collect {
                    placeListAdapter.submitList(it)
                }
            }

            lifecycleScope.launchWhenStarted {
                error.collect {
                    Snackbar.make(viewDataBinding.rootLayout, it, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    interface Listener {
        fun onConfirmButtonClick(place: Place?)
    }

    companion object {
        fun newInstance(
            onConfirmClick: (Place?) -> Unit
        ): FindLocationDialog {
            val fragment = FindLocationDialog()
            return fragment.apply {
                listener = object : Listener {
                    override fun onConfirmButtonClick(place: Place?) {
                        onConfirmClick.invoke(place)
                    }
                }
            }
        }

        const val TAG = "FindLocationDialog"
    }
}