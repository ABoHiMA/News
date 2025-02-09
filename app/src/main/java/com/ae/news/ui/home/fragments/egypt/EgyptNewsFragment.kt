package com.ae.news.ui.home.fragments.egypt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.ae.news.R
import com.ae.news.api.manager.ApiManager
import com.ae.news.databinding.FragmentEgyptNewsBinding
import com.ae.news.models.errorResponse.ErrorResponse
import com.ae.news.models.newsResponse.News
import com.ae.news.models.newsResponse.NewsResponse
import com.ae.news.ui.home.fragments.article.ArticleFragmentSheet
import com.ae.news.ui.home.fragments.news.NewsAdapter
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EgyptNewsFragment : Fragment() {
    private lateinit var viewBinding: FragmentEgyptNewsBinding
    private val adapter = NewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentEgyptNewsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEgyptView()
    }

    private fun initEgyptView() {
        viewBinding.rvEgy.adapter = adapter

        loadEgyptNews()
    }

    private fun loadEgyptNews() {
        showLoadingView()
        ApiManager.webServices().getSearchedNews(getString(R.string.egy))
            .enqueue(object : Callback<NewsResponse> {
                override fun onFailure(call: Call<NewsResponse>, error: Throwable) {
                    showErrorView(
                        error.localizedMessage ?: "Something went wrong"
                    ) { loadEgyptNews() }
                }

                override fun onResponse(
                    call: Call<NewsResponse>, response: Response<NewsResponse>
                ) {
                    if (!response.isSuccessful) {
                        val errorResponse = Gson().fromJson(
                            response.errorBody()?.string(), ErrorResponse::class.java
                        )
                        val message = errorResponse.message ?: "Something went wrong"
                        showErrorView(message) { loadEgyptNews() }
                        return
                    }
                    showSuccessView()
                    showEgyptNewsView(response.body()?.articles)
                }

            })
    }

    private fun showEgyptNewsView(newsList: List<News?>?) {
        adapter.setNews(newsList) { onArticleClick(it) }
    }

    private fun onArticleClick(news: News?) {
        val sheet = ArticleFragmentSheet.getInstance(news!!)
        sheet.show(requireActivity().supportFragmentManager, "")
    }

    private fun showLoadingView() {
        viewBinding.loading.isVisible = true
        viewBinding.error.isVisible = false
    }

    private fun showSuccessView() {
        viewBinding.loading.isVisible = false
        viewBinding.error.isVisible = false
    }

    private fun showErrorView(errorText: String?, onTryAgainClick: () -> Unit) {
        viewBinding.loading.isVisible = false
        viewBinding.error.isVisible = true
        viewBinding.tvError.text = errorText
        viewBinding.btnError.setOnClickListener {
            onTryAgainClick.invoke()
        }
    }
}