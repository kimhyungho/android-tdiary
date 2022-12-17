package com.hardy.yongbyung.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentSettingBinding
import com.hardy.yongbyung.dialog.AlertDialog
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import com.hardy.yongbyung.ui.webview.WebViewActivity
import com.hardy.yongbyung.utils.bind
import com.hardy.yongbyung.utils.clicks
import com.hardy.yongbyung.utils.throttleFirst
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseViewModelFragment<FragmentSettingBinding, SettingViewModel>(
    R.layout.fragment_setting
) {
    override val viewModel: SettingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            backButton.setOnClickListener {
                navController.popBackStack()
            }

            buttonLogout.clicks()
                .throttleFirst(1000L)
                .bind(lifecycleScope) {
                    val dialog = AlertDialog.newInstance(
                        "로그아웃 하시겠습니까?", "취소", "로그아웃",
                        onPositiveButtonClick = { viewModel?.logout() }
                    )
                    dialog.show(childFragmentManager, AlertDialog.TAG)
                }

            buttonWithdrawl.clicks()
                .throttleFirst(1000L)
                .bind(lifecycleScope) {
                    val dialog = AlertDialog.newInstance(
                        "정말 탈퇴하시겠습니까?", "취소", "회원탈퇴",
                        onPositiveButtonClick = { viewModel?.signOut() }
                    )
                    dialog.show(childFragmentManager, AlertDialog.TAG)
                }

            buttonPrivacy.setOnClickListener {
                requireContext().startActivity(
                    Intent(
                        requireContext(),
                        WebViewActivity::class.java
                    )
                )
            }
        }

        with(viewModel) {
            lifecycleScope.launchWhenStarted {
                error.collect {
                    Snackbar.make(viewDataBinding.rootLayout, it, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}