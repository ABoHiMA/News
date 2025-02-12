package com.ae.news.ui.web

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ae.news.databinding.ActivityWebViewBinding
import com.ae.news.utils.Utils


class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding
    private lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        url = intent.getStringExtra(Utils.URL) ?: Utils.GOOGLE

        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(url)

        binding.btnBack.setOnClickListener { finish() }
    }
}
