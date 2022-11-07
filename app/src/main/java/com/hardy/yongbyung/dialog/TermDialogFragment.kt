package com.hardy.yongbyung.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentTermDialogBinding
import com.hardy.yongbyung.ui.base.BaseDialogFragment
import com.hardy.yongbyung.utils.WebViewClient

class TermDialogFragment : BaseDialogFragment<FragmentTermDialogBinding>(
    R.layout.fragment_term_dialog
) {
    private val title: String by lazy { arguments?.getString(TITLE_KEY)!! }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWebView()

        with(viewDataBinding) {
            toolbar.startButtonClickListener = View.OnClickListener {
                dismiss()
            }
            toolbar.title = title
            webView.loadUrl(routeUrl(title))
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        with(viewDataBinding.webView) {
            webViewClient = WebViewClient(requireContext())
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
        }
    }

    private fun routeUrl(title: String): String {
        return when (title) {
            TERMS_OF_SERVICE -> "https://kimhyungho.tistory.com/42"
            else -> "https://kimhyungho.tistory.com/42"
        }
    }

    companion object TITLE {

        fun newInstance(
            title: String,
        ): TermDialogFragment {
            val fragment = TermDialogFragment()
            return fragment.apply {
                arguments = bundleOf(
                    TITLE_KEY to title,
                )
            }
        }

        const val TITLE_KEY = "TITLE_KEY"
        const val TERMS_OF_SERVICE = "서비스 이용약관"
        const val TAG = "SendMessageDialog"
    }
}