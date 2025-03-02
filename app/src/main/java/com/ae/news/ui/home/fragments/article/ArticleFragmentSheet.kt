package com.ae.news.ui.home.fragments.article

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ae.news.R
import com.ae.news.common.Utils
import com.ae.news.databinding.FragmentArticleSheetBinding
import com.ae.news.models.newsResponse.News
import com.ae.news.ui.web.WebViewActivity
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ArticleFragmentSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentArticleSheetBinding? = null
    private val binding get() = _binding!!
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
        _binding = FragmentArticleSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSheet()
    }

    private fun initSheet() {
        binding.txtTitle.text = article?.description

        Glide.with(binding.root).load(article?.urlToImage).error(R.drawable.ic_launcher)
            .into(binding.imgNews)

        binding.btnViewFull.setOnClickListener {
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            intent.putExtra(Utils.URL, article?.url ?: Utils.GOOGLE)
            startActivity(intent)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}