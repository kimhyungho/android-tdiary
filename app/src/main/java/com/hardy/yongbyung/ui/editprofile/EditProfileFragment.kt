package com.hardy.yongbyung.ui.editprofile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentEditProfileBinding
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileFragment : BaseViewModelFragment<FragmentEditProfileBinding, EditProfileViewModel>(
    R.layout.fragment_edit_profile
) {
    override val viewModel: EditProfileViewModel by viewModels()

    private lateinit var getResult: ActivityResultLauncher<Intent>

    private val takePhotoFromAlbumIntent =
        Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
            putExtra(
                Intent.EXTRA_MIME_TYPES,
                arrayOf("image/jpeg")
            )
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    result.data?.data.let { uri ->
                        viewModel.setProfileImage(uri)
                    }
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            profileImage.setOnClickListener {
                getResult.launch(takePhotoFromAlbumIntent)
            }

            toolbar.startButtonClickListener = View.OnClickListener {
                navController.popBackStack()
            }

            changeButton.setOnClickListener {
                viewModel?.onChangeButtonClick()
            }
        }

        with(viewModel) {
            lifecycleScope.launch {
                originNickname.collect {
                    viewModel.setNickname(it)
                    viewDataBinding.nicknameTextField.text =
                        Editable.Factory.getInstance().newEditable(it)
                }
            }

            lifecycleScope.launch {
                message.collect {
                    Snackbar.make(viewDataBinding.rootLayout, it, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}