package com.ae.news.ui.home.fragments.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ae.news.R
import com.ae.news.api.manager.ApiManager
import com.ae.news.common.ErrorState
import com.ae.news.models.errorResponse.ErrorResponse
import com.ae.news.models.newsResponse.News
import com.ae.news.models.source.Source
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class NewsViewModel : ViewModel() {
    val loadingState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<ErrorState>()
    val sourcesLiveData = MutableLiveData<List<Source?>?>()
    val newsLiveData = MutableLiveData<List<News?>?>()

    fun loadSources(categoryId: String) {
        loadingState.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiManager.webServices().getSources(categoryId)
                loadingState.postValue(false)
                sourcesLiveData.postValue(response.sources)
            } catch (error: Exception) {
                val message = error.localizedMessage ?: R.string.wrong.toString()
                errorState.postValue(ErrorState(message) { loadSources(categoryId) })
            } catch (errorHttp: HttpException) {
                val message = handleError(errorHttp)?.message ?: R.string.wrong.toString()
                errorState.postValue(ErrorState(message) { loadSources(categoryId) })
            }
        }
    }

    fun loadNews(sourceId: String) {
        loadingState.value = true

        viewModelScope.launch {
            try {
                val response = ApiManager.webServices().getNews(sourceId)
                newsLiveData.value = response.articles
                loadingState.value = false
            } catch (error: Exception) {
                val message = error.localizedMessage ?: R.string.wrong.toString()
                errorState.value = ErrorState(message) { loadNews(sourceId) }
                loadingState.value = false
            } catch (errorHttp: HttpException) {
                val message = handleError(errorHttp)?.message ?: R.string.wrong.toString()
                errorState.value = ErrorState(message) { loadNews(sourceId) }
                loadingState.value = false
            }
        }
    }

    private fun handleError(errorHttp: HttpException): ErrorResponse? {
        return Gson().fromJson(
            errorHttp.response()?.errorBody()?.string(), ErrorResponse::class.java
        )
    }

}