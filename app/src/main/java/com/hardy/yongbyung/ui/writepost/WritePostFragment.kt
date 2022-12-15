package com.hardy.yongbyung.ui.writepost

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gun0912.tedpermission.provider.TedPermissionProvider
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentWritePostBinding
import com.hardy.yongbyung.dialog.FindLocationDialog
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import com.hardy.yongbyung.utils.bind
import com.hardy.yongbyung.utils.clicks
import com.hardy.yongbyung.utils.throttleFirst
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WritePostFragment : BaseViewModelFragment<FragmentWritePostBinding, WritePostViewModel>(
    R.layout.fragment_write_post
) {
    override val viewModel: WritePostViewModel by viewModels()

    private lateinit var galleryResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setResultLauncher()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            locationBttuon.setOnClickListener {
                val dialog = FindLocationDialog.newInstance { place ->
                    viewModel?.setPlace(place)
                }
                dialog.show(childFragmentManager, FindLocationDialog.TAG)
            }


            galleryButton.setOnClickListener {
                doTakeGallery()
            }

            writeButton.clicks()
                .throttleFirst(1000L)
                .bind(lifecycleScope) {
                    viewModel?.writePost()
                }
        }
    }

    private fun doTakeGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/* video/*"
            action = Intent.ACTION_GET_CONTENT
        }
        galleryResult.launch(intent)
    }

    private fun setResultLauncher() {
        galleryResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val uri = result.data?.data
                    val mimeType = getMimeType(uri)
                    viewModel.setMedia(uri, mimeType)
                }
            }
    }

    private fun getMimeType(uri: Uri?): String? {
        val cR = TedPermissionProvider.context.contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri ?: return null))
    }
}