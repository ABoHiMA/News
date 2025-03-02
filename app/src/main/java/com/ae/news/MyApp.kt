package com.ae.news

import android.app.Application
import com.ae.news.common.Utils.restoreAppTheme
import com.ae.news.common.Utils.sharedPreferences

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        sharedPreferences = this.getSharedPreferences("AppPreferences", MODE_PRIVATE)
        restoreAppTheme()
    }
}