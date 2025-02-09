package com.ae.news.ui.web

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ae.news.databinding.ActivityWebViewBinding


class WebViewActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityWebViewBinding
    private lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        viewBinding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        url = intent.getStringExtra("url") ?: ""

        viewBinding.webView.settings.javaScriptEnabled = true
        viewBinding.webView.loadUrl(url)

        viewBinding.btnBack.setOnClickListener { finish() }
    }
}
