package com.hardy.yongbyung.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.hardy.domain.model.Post
import com.hardy.yongbyung.R
import com.hardy.yongbyung.adapters.MonthAdapter
import com.hardy.yongbyung.adapters.RecentPostAdapter
import com.hardy.yongbyung.databinding.FragmentHomeBinding
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import com.hardy.yongbyung.utils.DateUtil
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class HomeFragment : BaseViewModelFragment<FragmentHomeBinding, HomeViewModel>(
    R.layout.fragment_home
) {
    override val viewModel: HomeViewModel by viewModels()

    private val monthListAdapter = MonthAdapter().apply {
        listener = object : MonthAdapter.Listener {
            override fun onDayClick(post: Post?, date: Date) {
                if (post == null) navController.navigate(
                    HomeFragmentDirections.actionHomeFragmentToDestWritePost(
                        DateUtil.dateToString(date)!!
                    )
                )
            }
        }
    }

    private val recentPostAdapter = RecentPostAdapter().apply {
        listener = object : RecentPostAdapter.Listener {
            override fun onItemClick(item: Post) {

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewDataBinding) {
            calendarCustom.apply {
                adapter = monthListAdapter
                if (savedInstanceState == null) {
                    scrollToPosition(Int.MAX_VALUE / 2)
                }
                val snap = PagerSnapHelper()
                snap.attachToRecyclerView(this)
            }

            recentPostRecyclerView.adapter = recentPostAdapter
        }

        with(viewModel) {
            lifecycleScope.launchWhenCreated {
                posts.collect {
                    Log.d("kkkk", it.toString())
                    monthListAdapter.setPosts(it)
                    recentPostAdapter.submitList(it)
                }
            }
        }
    }
}

