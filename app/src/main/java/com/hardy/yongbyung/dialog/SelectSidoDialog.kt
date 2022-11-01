package com.hardy.yongbyung.dialog

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hardy.yongbyung.R
import com.hardy.yongbyung.adapters.MainRegionListAdapter
import com.hardy.yongbyung.adapters.SubRegionListAdapter
import com.hardy.yongbyung.databinding.FragmentSelectSidoDialogBinding
import com.hardy.yongbyung.ui.base.BaseViewModelDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectSidoDialog :
    BaseViewModelDialogFragment<FragmentSelectSidoDialogBinding, SelectSidoViewModel>(
        R.layout.fragment_select_sido_dialog
    ) {

    private val selectedMainRegion: String? by lazy { arguments?.getString(MAIN_REGION_KEY) }
    private val selectedSubRegion: String? by lazy { arguments?.getString(SUB_REGION_KEY) }

    override val viewModel: SelectSidoViewModel by viewModels()

    var listener: Listener? = null

    private val mainRegionListAdapter = MainRegionListAdapter().apply {
        listener = object : MainRegionListAdapter.Listener {
            override fun onItemClick(name: String) {
                viewModel.onMainRegionSelect(name)
            }
        }
    }

    private val subRegionListAdapter = SubRegionListAdapter().apply {
        listener = object : SubRegionListAdapter.Listener {
            override fun onItemClick(name: String) {
                viewModel.onSubRegionSelect(name)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setRegionFilter(selectedMainRegion, selectedSubRegion)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            with(mainRegionRecyclerView) {
                itemAnimator = null
                adapter = mainRegionListAdapter
            }

            with(subRegionRecyclerView) {
                itemAnimator = null
                adapter = subRegionListAdapter
            }

            confirmButton.setOnClickListener {
                val mainRegion = viewModel?.mainRegions?.value?.firstOrNull { it.isSelected }?.name
                    ?: return@setOnClickListener
                val subRegion = viewModel?.subRegions?.value?.firstOrNull { it.isSelected }?.name
                    ?: return@setOnClickListener
                listener?.onItemClick(mainRegion, subRegion)
                dismiss()
            }

            closeButton.setOnClickListener {
                dismiss()
            }
        }

        with(viewModel) {
            lifecycleScope.launch {
                mainRegions.collect {
                    Log.d("kkkk", it.toString())
                    mainRegionListAdapter.submitList(it)
                }
            }

            lifecycleScope.launch {
                subRegions.collect {
                    subRegionListAdapter.submitList(it)
                }
            }
        }
    }

    interface Listener {
        fun onItemClick(mainRegion: String, subRegion: String)
    }

    companion object {
        fun newInstance(
            selectedMainRegion: String?,
            selectedSubRegion: String?,
            onConfirmClick: (String, String) -> Unit
        ): SelectSidoDialog {
            val fragment = SelectSidoDialog()
            return fragment.apply {
                arguments = bundleOf(
                    MAIN_REGION_KEY to selectedMainRegion,
                    SUB_REGION_KEY to selectedSubRegion
                )

                listener = object : Listener {
                    override fun onItemClick(mainRegion: String, subRegion: String) {
                        onConfirmClick.invoke(mainRegion, subRegion)
                    }
                }
            }
        }


        const val MAIN_REGION_KEY = "MAIN_REGION_KEY"
        const val SUB_REGION_KEY = "SUB_REGION_KEY"

        const val TAG = "SelectSidoDialog"
    }
}