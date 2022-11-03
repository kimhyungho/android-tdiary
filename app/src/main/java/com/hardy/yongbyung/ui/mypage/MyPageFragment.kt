package com.hardy.yongbyung.ui.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentMyPageBinding
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import com.hardy.yongbyung.ui.main.MainFragmentDirections
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
                mainNavController?.navigate(MainFragmentDirections.actionDestMainToDestEditProfile())
            }

            myPostButton.root.setOnClickListener {
                val uid = viewModel?.uid ?: return@setOnClickListener
                val mainNavController = parentFragment?.parentFragment?.findNavController()
                mainNavController?.navigate(MainFragmentDirections.actionDestMainToDestProfile(uid))
            }

            logoutButton.root.setOnClickListener {
                viewModel?.logout()
            }
        }
    }
}