package com.ae.news.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    private val apiKey = "741575f8a83148c98fa0351474fec62c"
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
        newRequest.addHeader("X-Api-Key", apiKey)
        return chain.proceed(newRequest.build())
    }
}