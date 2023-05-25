package com.xy.android.sdk.view.webview

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.xy.android.sdk.BackActivity
import com.xy.android.sdk.databinding.ActivityWebViewBinding

class WebViewActivity : BackActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.webView.apply {
            webViewClient = object : WebViewClient() {

                private fun overrideUrlLoading(url: String?): Boolean {
                    if (url?.startsWith(URL_SCHEME_WEIXIN) == true) {
                        try {
                            val intent = Intent()
                            intent.data = Uri.parse(url)
                            startActivity(intent)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        return true
                    }
                    return false
                }

                override fun shouldOverrideUrlLoading(webView: WebView?, url: String?): Boolean {
                    if (overrideUrlLoading(url)) {
                        return true
                    }
                    return super.shouldOverrideUrlLoading(webView, url)
                }

                override fun shouldOverrideUrlLoading(
                    webView: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    val url = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        request?.url?.toString()
                    } else {
                        null
                    }
                    if (overrideUrlLoading(url)) {
                        return true
                    }
                    return super.shouldOverrideUrlLoading(webView, request)
                }
            }
            loadUrl("file:///android_asset/webview-test.html")
        }
    }

    private companion object {

        private const val URL_SCHEME_WEIXIN = "weixin://"
    }
}