package com.ae.domain.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat

@Parcelize
data class News(
    val publishedAt: String? = null,
    val author: String? = null,
    val urlToImage: String? = null,
    val description: String? = null,
    val title: String? = null,
    val url: String? = null,
    val content: String? = null
) : Parcelable {
    fun getAuthorName(): String {
        return if (author != null) "By: $author" else ""
    }

    @SuppressLint("SimpleDateFormat")
    fun getFormattedDate(): Long? {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val formattedDate = publishedAt?.let { simpleDateFormat.parse(it) }
        return formattedDate?.time
    }
}