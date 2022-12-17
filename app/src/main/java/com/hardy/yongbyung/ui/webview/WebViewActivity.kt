package com.hardy.yongbyung.ui.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.ActivityWebViewBinding
import com.hardy.yongbyung.ui.base.BaseActivity
import com.hardy.yongbyung.utils.WebViewClient

class WebViewActivity : BaseActivity<ActivityWebViewBinding>(
    R.layout.activity_web_view
) {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWebView()

        with(viewDataBinding) {
            toolbar.startButtonClickListener = View.OnClickListener {
                finish()
            }

            webView.loadUrl("https://kimhyungho.tistory.com/42")
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        with(viewDataBinding.webView) {
            webViewClient = WebViewClient(this@WebViewActivity)
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
        }
    }
}