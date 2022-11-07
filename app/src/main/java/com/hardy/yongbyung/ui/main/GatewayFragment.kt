package com.hardy.yongbyung.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentGatewayBinding
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GatewayFragment : BaseViewModelFragment<FragmentGatewayBinding, GatewayViewModel>(
    R.layout.fragment_gateway
) {
    override val viewModel: GatewayViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBottomNavigation()
    }


    private fun setupBottomNavigation() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = navHostFragment.findNavController()
        viewDataBinding.mainBottomNavigation.setupWithNavController(navController)
    }
}