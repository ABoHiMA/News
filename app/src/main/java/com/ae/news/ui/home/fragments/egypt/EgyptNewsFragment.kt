package com.ae.news.ui.home.fragments.egypt

import androidx.fragment.app.Fragment

class EgyptNewsFragment : Fragment() {
    /*
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
        initEgyptView()
    }

    private fun initEgyptView() {
        binding.rvEgy.adapter = adapter

        loadEgyptNews()
    }

    private fun loadEgyptNews() {
        showLoadingView()
        com.ae.data.api.manager.ApiManager.webServices().getSearchedNews(getString(R.string.egy))
            .enqueue(object : Callback<com.ae.data.models.newsResponse.NewsResponse> {
                override fun onFailure(call: Call<com.ae.data.models.newsResponse.NewsResponse>, error: Throwable) {
                    showErrorView(
                        error.localizedMessage ?: getString(R.string.wrong)
                    ) { loadEgyptNews() }
                }

                override fun onResponse(
                    call: Call<com.ae.data.models.newsResponse.NewsResponse>, response: Response<com.ae.data.models.newsResponse.NewsResponse>
                ) {
                    if (!response.isSuccessful) {
                        val errorResponse = Gson().fromJson(
                            response.errorBody()?.string(), com.ae.data.models.errorResponse.ErrorResponse::class.java
                        )
                        val message = errorResponse.message ?: getString(R.string.wrong)
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

     */
}