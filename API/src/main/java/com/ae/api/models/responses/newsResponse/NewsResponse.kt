package com.ae.api.models.responses.newsResponse

import com.ae.api.models.news.NewsDTO
import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @field:SerializedName("totalResults")
    val totalResults: Int? = null,

    @field:SerializedName("articles")
    val articles: List<NewsDTO?>? = null,

    @field:SerializedName("status")
    val status: String? = null
)