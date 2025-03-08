package com.ae.news.bindingAdapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ae.news.R
import com.ae.news.common.Utils.imageWithGlide
import com.github.marlonlom.utilities.timeago.TimeAgo

@BindingAdapter("app:imageUrl")
fun bindNewsImage(imageView: ImageView, url: String?) {
    if (url.isNullOrEmpty()) {
        imageView.setImageResource(R.drawable.ic_launcher)
    } else {
        imageWithGlide(imageView = imageView, urlToImage = url)
    }
}

@BindingAdapter("app:timeAgo")
fun getFormattedPublishAt(textView: TextView, formatDate: Long?) {
    textView.text = formatDate?.let { TimeAgo.using(it) } ?: ""
}