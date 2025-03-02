package com.ae.news.ui.home.fragments.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ae.news.common.ErrorState
import com.ae.news.databinding.FragmentNewsBinding
import com.ae.news.models.categories.Category
import com.ae.news.models.newsResponse.News
import com.ae.news.models.source.Source
import com.ae.news.ui.home.fragments.article.ArticleFragmentSheet
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class NewsFragment : Fragment() {
    val viewModel: NewsViewModel by viewModels<NewsViewModel>()
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
        observeLiveDate()
        initRecycler()
        category?.let { viewModel.loadSources(it.id) }
    }

    private fun observeLiveDate() {
        viewModel.loadingState.observe(viewLifecycleOwner) {
            if (it) {
                showLoadingView()
            } else {
                showSuccessView()
            }
        }
        viewModel.errorState.observe(viewLifecycleOwner) { showErrorView(it) }
        viewModel.sourcesLiveData.observe(viewLifecycleOwner) { bindTabsView(it) }
        viewModel.newsLiveData.observe(viewLifecycleOwner) { bindNewsView(it) }

    }

    private fun initRecycler() {
        binding.rvNews.adapter = adapter
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
                source.id?.let { viewModel.loadNews(it) }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                val source = tab?.tag as Source
                source.id?.let { viewModel.loadNews(it) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })
        binding.tabsSources.getTabAt(0)?.select()
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

    private fun showErrorView(errorState: ErrorState) {
        binding.loading.isVisible = false
        binding.error.isVisible = true
        binding.tvError.text = errorState.errorMessage
        binding.btnError.setOnClickListener {
            errorState.onRetry?.invoke()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}