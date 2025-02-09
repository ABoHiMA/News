package com.ae.news.api.manager

import android.util.Log
import com.ae.news.api.interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {

    companion object {
        private var retrofit: Retrofit? = null

        private fun initRetrofit(): Retrofit {
            if (retrofit == null) {
                val loggingInterceptor = HttpLoggingInterceptor { message ->
                    Log.e("myAPI", message)
                }
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                val okHttpClient = OkHttpClient
                    .Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(AuthInterceptor())
                    .build()
                retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .baseUrl("https://newsapi.org/").build()
            }
            return retrofit!!
        }

        fun webServices(): WebServices = initRetrofit().create(WebServices::class.java)
    }
}