package com.hardy.yongbyung

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.hardy.yongbyung.databinding.ActivityMainBinding
import com.hardy.yongbyung.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    R.layout.activity_main
) {
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.container) as? NavHostFragment)?.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController?.setGraph(R.navigation.nav_onboarding)
    }
}