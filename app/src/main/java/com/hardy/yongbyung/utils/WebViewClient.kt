package com.hardy.yongbyung.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.net.http.SslError
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import com.hardy.yongbyung.R

class WebViewClient(
    private val context: Context
) : WebViewClient() {
    @SuppressLint("WebViewClientOnReceivedSslError")
    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        super.onReceivedSslError(view, handler, error)

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage(context.getString(R.string.not_secure_certification))
        builder.setPositiveButton(context.getString(R.string.do_continue)) { _, _ -> handler?.proceed() }
        builder.setNegativeButton(context.getString(R.string.cancel)) { _, _ -> handler?.cancel() }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}