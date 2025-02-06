package com.ae.news.ui.home.fragments.article

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ae.news.R
import com.ae.news.databinding.FragmentArticleSheetBinding
import com.ae.news.models.newsResponse.News
import com.ae.news.ui.web.WebViewActivity
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ArticleFragmentSheet : BottomSheetDialogFragment() {
    private lateinit var viewBinding: FragmentArticleSheetBinding
    private var article: News? = null

    companion object {
        fun getInstance(news: News): ArticleFragmentSheet {
            val fragment = ArticleFragmentSheet()
            fragment.article = news
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentArticleSheetBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSheet()
    }

    private fun initSheet() {
        viewBinding.txtTitle.text = article?.description

        Glide.with(viewBinding.root).load(article?.urlToImage).error(R.drawable.ic_launcher)
            .into(viewBinding.imgNews)

        viewBinding.btnViewFull.setOnClickListener {
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            intent.putExtra("url", article?.url ?: "https://www.google.com")
            startActivity(intent)
            dismiss()
        }
    }

}