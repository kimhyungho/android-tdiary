package com.hardy.yongbyung.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.hardy.yongbyung.R
import com.hardy.yongbyung.adapters.MonthAdapter
import com.hardy.yongbyung.databinding.FragmentHomeBinding
import com.hardy.yongbyung.ui.base.BaseViewModelFragment

class HomeFragment : BaseViewModelFragment<FragmentHomeBinding, HomeViewModel>(
    R.layout.fragment_home
) {
    override val viewModel: HomeViewModel by viewModels()

    val monthListManager by lazy {
        LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    val monthListAdapter = MonthAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            toolbar.endFirstButtonClickListener = View.OnClickListener {

            }

            calendarCustom.apply {
                layoutManager = monthListManager
                adapter = monthListAdapter
                scrollToPosition(Int.MAX_VALUE / 2)

                val snap = PagerSnapHelper()
                snap.attachToRecyclerView(this)
            }
        }



        with(viewModel) {

        }
    }
}