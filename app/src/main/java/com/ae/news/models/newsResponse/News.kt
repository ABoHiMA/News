package com.ae.news.models.newsResponse

import android.annotation.SuppressLint
import android.os.Parcelable
import com.ae.news.models.source.Source
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat

@Parcelize
data class News(

    @field:SerializedName("publishedAt")
    val publishedAt: String? = null,

    @field:SerializedName("author")
    val author: String? = null,

    @field:SerializedName("urlToImage")
    val urlToImage: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("source")
    val source: Source? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("content")
    val content: String? = null
) : Parcelable {
    @SuppressLint("SimpleDateFormat")
    fun formattedPublishAt(): Long? {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val formattedDate = publishedAt?.let { simpleDateFormat.parse(it) }
        return formattedDate?.time
    }
}