package com.ae.news.ui.home.fragments.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.ae.news.R
import com.ae.news.api.manager.ApiManager
import com.ae.news.databinding.FragmentNewsBinding
import com.ae.news.models.categories.Category
import com.ae.news.models.errorResponse.ErrorResponse
import com.ae.news.models.newsResponse.News
import com.ae.news.models.newsResponse.NewsResponse
import com.ae.news.models.source.Source
import com.ae.news.models.sourcesResponse.SourcesResponse
import com.ae.news.ui.home.fragments.article.ArticleFragmentSheet
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private var adapter = NewsAdapter()
    private var category: Category? = null

    companion object {
        fun getInstance(category: Category): NewsFragment {
            val fragment = NewsFragment()
            fragment.category = category
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        loadSources()
    }

    private fun initRecycler() {
        binding.rvNews.adapter = adapter
    }

    private fun loadSources() {
        showLoadingView()
        ApiManager.webServices().getSources(category!!.id)
            .enqueue(object : Callback<SourcesResponse> {

                override fun onFailure(response: Call<SourcesResponse>, error: Throwable) {
                    showErrorView(
                        error.localizedMessage ?: getString(R.string.wrong)
                    ) { loadSources() }
                }

                override fun onResponse(
                    call: Call<SourcesResponse>, response: Response<SourcesResponse>
                ) {
                    if (!response.isSuccessful) {
                        val errorResponse = Gson().fromJson(
                            response.errorBody()?.string(), ErrorResponse::class.java
                        )
                        val message = errorResponse.message ?: getString(R.string.wrong)
                        showErrorView(message) { loadSources() }
                        return
                    }
                    showSuccessView()
                    bindTabsView(response.body()?.sources)
                }
            })
    }

    private fun bindTabsView(sources: List<Source?>?) {
        sources?.forEach { source ->
            val tab = binding.tabsSources.newTab()
            tab.text = source?.name
            tab.tag = source
            binding.tabsSources.addTab(tab)
        }
        binding.tabsSources.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val source = tab?.tag as Source
                source.id?.let { loadNews(it) }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                val source = tab?.tag as Source
                source.id?.let { loadNews(it) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })
        binding.tabsSources.getTabAt(0)?.select()
    }

    private fun loadNews(sourceId: String) {
        showLoadingView()
        ApiManager.webServices().getNews(sourceId).enqueue(object : Callback<NewsResponse> {

            override fun onFailure(response: Call<NewsResponse>, error: Throwable) {
                showErrorView(
                    error.localizedMessage ?: getString(R.string.wrong)
                ) { loadNews(sourceId) }
            }

            override fun onResponse(
                call: Call<NewsResponse>, response: Response<NewsResponse>
            ) {
                if (!response.isSuccessful) {
                    val errorResponse = Gson().fromJson(
                        response.errorBody()?.string(), ErrorResponse::class.java
                    )
                    val message = errorResponse.message ?: getString(R.string.wrong)
                    showErrorView(message) { loadNews(sourceId) }
                    return
                }
                showSuccessView()
                bindNewsView(response.body()?.articles)
            }
        })
    }

    private fun bindNewsView(newsList: List<News?>?) {
        adapter.setNews(newsList) { onNewsClick(it) }
    }

    private fun onNewsClick(news: News?) {
        val sheet = ArticleFragmentSheet.getInstance(news!!)
        sheet.show(requireActivity().supportFragmentManager, "")
    }

    private fun showLoadingView() {
        binding.loading.isVisible = true
        binding.error.isVisible = false
    }

    private fun showSuccessView() {
        binding.loading.isVisible = false
        binding.error.isVisible = false
    }

    private fun showErrorView(errorText: String?, onTryAgainClick: () -> Unit) {
        binding.loading.isVisible = false
        binding.error.isVisible = true
        binding.tvError.text = errorText
        binding.btnError.setOnClickListener {
            onTryAgainClick.invoke()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}