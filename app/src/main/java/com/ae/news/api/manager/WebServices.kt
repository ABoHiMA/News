package com.ae.news.api.manager

import com.ae.news.models.newsResponse.NewsResponse
import com.ae.news.models.sourcesResponse.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    @GET("v2/top-headlines/sources")
    suspend fun getSources(@Query("category") categoryId: String): SourcesResponse

    @GET("v2/everything")
    suspend fun getNews(@Query("sources") source: String): NewsResponse

    @GET("v2/everything")
    fun getSearchedNews(@Query("q") query: String): Call<NewsResponse>
}