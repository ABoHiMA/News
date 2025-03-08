package com.ae.news.bindingAdapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ae.news.R
import com.bumptech.glide.Glide
import com.github.marlonlom.utilities.timeago.TimeAgo

@BindingAdapter("app:imageUrl")
fun bindNewsImage(imageView: ImageView, url: String) {
    Glide.with(imageView).load(url).error(R.drawable.ic_launcher).into(imageView)
}

@BindingAdapter("app:timeAgo")
fun getFormattedPublishAt(textView: TextView, formatDate: Long?): String {
    return formatDate?.let { TimeAgo.using(it) } ?: ""
}