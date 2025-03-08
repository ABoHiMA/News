package com.ae.news.ui.home.fragments.egypt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ae.domain.models.News
import com.ae.news.R
import com.ae.news.common.ErrorState
import com.ae.news.databinding.FragmentEgyptNewsBinding
import com.ae.news.ui.home.fragments.article.ArticleFragmentSheet
import com.ae.news.ui.home.fragments.news.NewsAdapter
import com.ae.news.ui.home.fragments.news.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EgyptNewsFragment : Fragment() {
    private val viewModel: NewsViewModel by viewModels<NewsViewModel>()
    private var _binding: FragmentEgyptNewsBinding? = null
    private val binding get() = _binding!!
    private val adapter = NewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEgyptNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLiveData()
        initEgyptView()
    }

    private fun observeLiveData() {
        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                showLoadingView()
            } else {
                showSuccessView()
            }
        }
        viewModel.errorState.observe(viewLifecycleOwner) {
            showErrorView(it)
        }
        viewModel.newsLiveData.observe(viewLifecycleOwner) { newsList ->
            showEgyptNewsView(newsList)
        }
    }

    private fun initEgyptView() {
        binding.rvEgy.adapter = adapter
        loadEgyptNews()
    }

    private fun loadEgyptNews() {
        viewModel.loadNews(query = getString(R.string.egy))
    }

    private fun showEgyptNewsView(newsList: List<News?>?) {
        adapter.setNews(newsList) { onArticleClick(it) }
    }

    private fun onArticleClick(news: News?) {
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