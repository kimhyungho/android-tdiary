package com.hardy.yongbyung

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.hardy.yongbyung.databinding.ActivityMainBinding
import com.hardy.yongbyung.ui.base.BaseViewModelActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NavigationActivity : BaseViewModelActivity<ActivityMainBinding, NavigationViewModel>(
    R.layout.activity_main
) {
    override val viewModel: NavigationViewModel by viewModels()

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.container) as? NavHostFragment)?.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            viewModel.isAuthenticated.collect {
                if (it) navController?.setGraph(R.navigation.nav_main)
                else navController?.setGraph(R.navigation.nav_onboarding)
            }
        }
    }
}