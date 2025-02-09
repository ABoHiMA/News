package com.ae.news.ui.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.ae.news.api.manager.ApiManager
import com.ae.news.databinding.ActivitySearchBinding
import com.ae.news.models.errorResponse.ErrorResponse
import com.ae.news.models.newsResponse.News
import com.ae.news.models.newsResponse.NewsResponse
import com.ae.news.ui.home.fragments.article.ArticleFragmentSheet
import com.ae.news.ui.home.fragments.news.NewsAdapter
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySearchBinding
    private val adapter = NewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        showEmptyView()
        initSearchView()
    }

    private fun initSearchView() {
        viewBinding.rvSearch.adapter = adapter

        viewBinding.btnBack.setOnClickListener { finish() }

        viewBinding.etSearch.addTextChangedListener { text ->
            if (text.toString().isBlank() || text.toString().isEmpty()) showEmptyView()
            else loadNews(text.toString())
        }

        viewBinding.etSearch.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard(v)
                return@setOnEditorActionListener true
            }
            false
        }

    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun loadNews(query: String) {
        showLoadingView()
        ApiManager.webServices().getSearchedNews(query).enqueue(object : Callback<NewsResponse> {
            override fun onFailure(call: Call<NewsResponse>, error: Throwable) {
                showErrorView(
                    error.localizedMessage ?: "Something went wrong"
                ) { loadNews(query) }
            }

            override fun onResponse(
                call: Call<NewsResponse>, response: Response<NewsResponse>
            ) {
                if (!response.isSuccessful) {
                    val errorResponse = Gson().fromJson(
                        response.errorBody()?.string(), ErrorResponse::class.java
                    )
                    val message = errorResponse.message ?: "Something went wrong"
                    showErrorView(message) { loadNews(query) }
                    return
                }
                showSuccessView()
                showSearchedNewsView(response.body()?.articles)
            }

        })
    }

    private fun showSearchedNewsView(newsList: List<News?>?) {
        adapter.setNews(newsList) { onArticleClick(it) }
    }

    private fun onArticleClick(news: News?) {
        val sheet = ArticleFragmentSheet.getInstance(news!!)
        sheet.show(supportFragmentManager, "")
    }

    private fun showLoadingView() {
        viewBinding.loading.isVisible = true
        viewBinding.empty.isVisible = false
        viewBinding.error.isVisible = false
    }

    private fun showEmptyView() {
        viewBinding.empty.isVisible = true
        viewBinding.loading.isVisible = false
        viewBinding.error.isVisible = false
    }

    private fun showSuccessView() {
        viewBinding.loading.isVisible = false
        viewBinding.empty.isVisible = false
        viewBinding.error.isVisible = false
    }

    private fun showErrorView(errorText: String?, onTryAgainClick: () -> Unit) {
        viewBinding.loading.isVisible = false
        viewBinding.empty.isVisible = false
        viewBinding.error.isVisible = true
        viewBinding.tvError.text = errorText
        viewBinding.btnError.setOnClickListener {
            onTryAgainClick.invoke()
        }
    }

}