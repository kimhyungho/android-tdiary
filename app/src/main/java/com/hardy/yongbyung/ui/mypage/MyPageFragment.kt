package com.hardy.yongbyung.ui.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentMyPageBinding
import com.hardy.yongbyung.dialog.AlertDialog
import com.hardy.yongbyung.dialog.TermDialogFragment
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import com.hardy.yongbyung.ui.main.GatewayFragmentDirections
import com.hardy.yongbyung.utils.bind
import com.hardy.yongbyung.utils.clicks
import com.hardy.yongbyung.utils.throttleFirst
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseViewModelFragment<FragmentMyPageBinding, MyPageViewModel>(
    R.layout.fragment_my_page
) {
    override val viewModel: MyPageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            editProfileButton.setOnClickListener {
                val mainNavController = parentFragment?.parentFragment?.findNavController()
                mainNavController?.navigate(GatewayFragmentDirections.actionDestMainToDestEditProfile())
            }

            myPostButton.root.setOnClickListener {
                val uid = viewModel?.uid ?: return@setOnClickListener
                val mainNavController = parentFragment?.parentFragment?.findNavController()
                mainNavController?.navigate(
                    GatewayFragmentDirections.actionDestMainToDestProfile(
                        uid
                    )
                )
            }
            termsOfServiceButton.root.setOnClickListener {
                val dialog = TermDialogFragment.newInstance(TermDialogFragment.TERMS_OF_SERVICE)
                dialog.show(childFragmentManager, TermDialogFragment.TAG)
            }

            logoutButton.root.clicks()
                .throttleFirst(1000L)
                .bind(lifecycleScope) {
                    val dialog = AlertDialog.newInstance(
                        "로그아웃 하시겠습니까?",
                        "취소",
                        "로그아웃",
                        onPositiveButtonClick = { viewModel?.logout() }
                    )
                    dialog.show(childFragmentManager, AlertDialog.TAG)
                }

            signOutButton.root.clicks()
                .throttleFirst(1000L)
                .bind(lifecycleScope) {
                    val dialog = AlertDialog.newInstance(
                        "정말로 탈퇴하시겠습니까?",
                        "취소",
                        "회원탈퇴",
                        onPositiveButtonClick = { viewModel?.signOut() }
                    )
                    dialog.show(childFragmentManager, AlertDialog.TAG)
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