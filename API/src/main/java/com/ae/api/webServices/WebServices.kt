package com.ae.api.webServices

import com.ae.api.models.responses.newsResponse.NewsResponse
import com.ae.api.models.responses.sourcesResponse.SourcesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    @GET("v2/top-headlines/sources")
    suspend fun getSources(@Query("category") categoryId: String): SourcesResponse

    @GET("v2/everything")
    suspend fun getNews(
        @Query("sources") source: String? = null,
        @Query("q") query: String? = null,
    ): NewsResponse

//    @GET("v2/everything")
//    fun getSearchedNews(@Query("q") query: String): Call<NewsResponse>
}