package com.ae.news.api.manager

import com.ae.news.models.newsResponse.NewsResponse
import com.ae.news.models.sourcesResponse.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    @GET("v2/top-headlines/sources")
    fun getSources(@Query("category") categoryId: String): Call<SourcesResponse>

    @GET("v2/everything")
    fun getNews(@Query("sources") source: String): Call<NewsResponse>
}