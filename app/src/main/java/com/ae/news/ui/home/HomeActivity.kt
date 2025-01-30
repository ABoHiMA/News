package com.ae.news.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ae.news.R
import com.ae.news.databinding.ActivityHomeBinding
import com.ae.news.ui.home.tabs.NewsFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, NewsFragment())
            .commit()

    }

}