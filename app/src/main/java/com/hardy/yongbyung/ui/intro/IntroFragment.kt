package com.hardy.yongbyung.ui.intro

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentIntroBinding
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
//class IntroFragment : BaseViewModelFragment<FragmentIntroBinding, IntroViewModel>(
//    R.layout.fragment_intro
//) {
//    override val viewModel: IntroViewModel by viewModels()
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        with(viewModel) {
//            lifecycleScope.launchWhenCreated {
//                showLoginPage.collect {
//                    if (it != null) navController.navigate(IntroFragmentDirections.actionDestIntroToDestLogin())
//                }
//            }
//        }
//    }
//}