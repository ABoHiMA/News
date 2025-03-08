package com.ae.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    private val apiKey = "741575f8a83148c98fa0351474fec62c"
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
        newRequest.addHeader("X-Api-Key", apiKey)
        return chain.proceed(newRequest.build())
    }
}