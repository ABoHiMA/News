package com.ae.news.ui.home.fragments.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ae.news.R
import com.ae.news.api.manager.ApiManager
import com.ae.news.common.ErrorState
import com.ae.news.models.errorResponse.ErrorResponse
import com.ae.news.models.newsResponse.News
import com.ae.news.models.newsResponse.NewsResponse
import com.ae.news.models.source.Source
import com.ae.news.models.sourcesResponse.SourcesResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {
    val loadingState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<ErrorState>()
    val sourcesLiveData = MutableLiveData<List<Source?>?>()
    val newsLiveData = MutableLiveData<List<News?>?>()

    fun loadSources(categoryId: String) {
        loadingState.value = true
        ApiManager.webServices().getSources(categoryId).enqueue(object : Callback<SourcesResponse> {

            override fun onFailure(response: Call<SourcesResponse>, error: Throwable) {
                errorState.value =
                    ErrorState(errorMessage = error.localizedMessage ?: R.string.wrong.toString(),
                        onRetry = { loadSources(categoryId) })
            }

            override fun onResponse(
                call: Call<SourcesResponse>, response: Response<SourcesResponse>
            ) {
                if (!response.isSuccessful) {
                    val errorResponse = Gson().fromJson(
                        response.errorBody()?.string(), ErrorResponse::class.java
                    )
                    val message = errorResponse.message ?: R.string.wrong.toString()
                    errorState.value = ErrorState(message) { loadSources(categoryId) }
                    return
                }
                loadingState.value = false
                sourcesLiveData.value = response.body()?.sources
            }
        })
    }

    fun loadNews(sourceId: String) {
        loadingState.value = true
        ApiManager.webServices().getNews(sourceId).enqueue(object : Callback<NewsResponse> {

            override fun onFailure(response: Call<NewsResponse>, error: Throwable) {
                errorState.value = ErrorState(errorMessage = error.localizedMessage
                    ?: R.string.wrong.toString(),
                    onRetry = { loadNews(sourceId) })
            }

            override fun onResponse(
                call: Call<NewsResponse>, response: Response<NewsResponse>
            ) {
                if (!response.isSuccessful) {
                    val errorResponse = Gson().fromJson(
                        response.errorBody()?.string(), ErrorResponse::class.java
                    )
                    val message = errorResponse.message ?: R.string.wrong.toString()
                    errorState.value = ErrorState(message) { loadNews(sourceId) }
                    return
                }
                loadingState.value = false
                newsLiveData.value = response.body()?.articles
            }
        })
    }


}