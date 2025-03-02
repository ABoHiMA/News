package com.ae.news.bindingAdapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.ae.news.R
import com.bumptech.glide.Glide

@BindingAdapter("app:imageUrl")
fun bindNewsImage(imageView: ImageView, url: String) {
    Glide.with(imageView).load(url).error(R.drawable.ic_launcher).into(imageView)
}