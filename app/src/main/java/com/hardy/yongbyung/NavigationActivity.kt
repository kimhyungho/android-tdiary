package com.hardy.yongbyung

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.hardy.yongbyung.databinding.ActivityNavigationBinding
import com.hardy.yongbyung.ui.base.BaseViewModelActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NavigationActivity : BaseViewModelActivity<ActivityNavigationBinding, NavigationViewModel>(
    R.layout.activity_navigation
) {
    override val viewModel: NavigationViewModel by viewModels()

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.container) as? NavHostFragment)?.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_navigation)

        lifecycleScope.launch {
            viewModel.isAuthenticated.collect {
                if (it) navController?.setGraph(R.navigation.nav_main)
                else navController?.setGraph(R.navigation.nav_onboarding)
            }
        }
    }
}