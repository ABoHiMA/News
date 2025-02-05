package com.ae.news.ui.home.fragments.news

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ae.news.R
import com.ae.news.databinding.ItemNewsBinding
import com.ae.news.models.newsResponse.News
import com.bumptech.glide.Glide
import com.github.marlonlom.utilities.timeago.TimeAgo

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private var newsList: List<News?>? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setNews(newsList: List<News?>?) {
        this.newsList = newsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding =
            ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = newsList?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = newsList?.get(position)
        holder.bindData(news)

    }

    inner class ViewHolder(val itemBinding: ItemNewsBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bindData(news: News?) {
            val authorName = if (news?.author != null) "By: ${news.author}" else ""
            val formattedDate = news?.formattedPublishAt()?.let { TimeAgo.using(it) } ?: ""

            Glide.with(itemBinding.root)
                .load(news?.urlToImage)
                .error(R.drawable.ic_general)
                .into(itemBinding.imgNews)

            itemBinding.txtTitle.text = news?.title
            itemBinding.txtAuthor.text = authorName
            itemBinding.txtDate.text = formattedDate

        }
    }

}
