package com.ae.news.ui.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.ae.domain.models.News
import com.ae.news.common.ErrorState
import com.ae.news.databinding.ActivitySearchBinding
import com.ae.news.ui.home.fragments.article.ArticleFragmentSheet
import com.ae.news.ui.home.fragments.news.NewsAdapter
import com.ae.news.ui.home.fragments.news.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private val viewModel: NewsViewModel by viewModels<NewsViewModel>()
    private lateinit var binding: ActivitySearchBinding
    private val adapter = NewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeLiveData()
        showEmptyView()
        initSearchView()
    }

    private fun observeLiveData() {
        viewModel.loadingState.observe(this) { isLoading ->
            if (isLoading) {
                showLoadingView()
            } else {
                showSuccessView()
            }
        }
        viewModel.errorState.observe(this) {
            showErrorView(it)
        }
        viewModel.newsLiveData.observe(this) { newsList ->
            showSearchedNewsView(newsList)
        }
    }

    private fun initSearchView() {
        binding.rvSearch.adapter = adapter

        binding.btnBack.setOnClickListener { finish() }

        binding.etSearch.addTextChangedListener { text ->
            if (text.toString().isBlank() || text.toString().isEmpty()) showEmptyView()
            else loadNews(text.toString())
        }

        binding.etSearch.setOnEditorActionListener { v, actionId, _ ->
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
        viewModel.loadNews(query = query)
    }

    private fun showSearchedNewsView(newsList: List<News?>?) {
        adapter.setNews(newsList) { onArticleClick(it) }
    }

    private fun onArticleClick(news: News?) {
        val sheet = ArticleFragmentSheet.getInstance(news!!)
        sheet.show(supportFragmentManager, "")
    }

    private fun showLoadingView() {
        binding.loading.isVisible = true
        binding.empty.isVisible = false
        binding.error.isVisible = false
    }

    private fun showEmptyView() {
        binding.empty.isVisible = true
        binding.loading.isVisible = false
        binding.error.isVisible = false
    }

    private fun showSuccessView() {
        binding.loading.isVisible = false
        binding.empty.isVisible = false
        binding.error.isVisible = false
    }

    private fun showErrorView(errorState: ErrorState) {
        binding.loading.isVisible = false
        binding.empty.isVisible = false
        binding.error.isVisible = true
        binding.tvError.text = errorState.errorMessage
        binding.btnError.setOnClickListener {
            errorState.onRetry?.invoke()
        }
    }
}