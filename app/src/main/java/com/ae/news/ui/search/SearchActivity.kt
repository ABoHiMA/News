package com.ae.news.ui.search

import androidx.appcompat.app.AppCompatActivity


class SearchActivity : AppCompatActivity() {
    /*
    private lateinit var binding: ActivitySearchBinding
    private val adapter = NewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showEmptyView()
        initSearchView()
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
        showLoadingView()
        com.ae.data.api.manager.ApiManager.webServices().getSearchedNews(query).enqueue(object : Callback<com.ae.data.models.newsResponse.NewsResponse> {
            override fun onFailure(call: Call<com.ae.data.models.newsResponse.NewsResponse>, error: Throwable) {
                showErrorView(
                    error.localizedMessage ?: getString(R.string.wrong)
                ) { loadNews(query) }
            }

            override fun onResponse(
                call: Call<com.ae.data.models.newsResponse.NewsResponse>, response: Response<com.ae.data.models.newsResponse.NewsResponse>
            ) {
                if (!response.isSuccessful) {
                    val errorResponse = Gson().fromJson(
                        response.errorBody()?.string(), com.ae.data.models.errorResponse.ErrorResponse::class.java
                    )
                    val message = errorResponse.message ?: getString(R.string.wrong)
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

    private fun showErrorView(errorText: String?, onTryAgainClick: () -> Unit) {
        binding.loading.isVisible = false
        binding.empty.isVisible = false
        binding.error.isVisible = true
        binding.tvError.text = errorText
        binding.btnError.setOnClickListener {
            onTryAgainClick.invoke()
        }
    }

     */
}