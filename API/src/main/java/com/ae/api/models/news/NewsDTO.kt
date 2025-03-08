package com.ae.api.models.news

import com.ae.api.models.source.SourceDTO
import com.ae.domain.models.News
import com.google.gson.annotations.SerializedName

data class NewsDTO(
    @field:SerializedName("publishedAt") val publishedAt: String? = null,

    @field:SerializedName("author") val author: String? = null,

    @field:SerializedName("urlToImage") val urlToImage: String? = null,

    @field:SerializedName("description") val description: String? = null,

    @field:SerializedName("source") val source: SourceDTO? = null,

    @field:SerializedName("title") val title: String? = null,

    @field:SerializedName("url") val url: String? = null,

    @field:SerializedName("content") val content: String? = null
) {
    fun toNews() = News(
        publishedAt = publishedAt,
        author = author,
        urlToImage = urlToImage,
        description = description,
        title = title,
        url = url,
        content = content,
    )
}