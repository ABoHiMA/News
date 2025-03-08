package com.ae.news.ui.home.fragments.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ae.domain.models.News
import com.ae.domain.models.Source
import com.ae.domain.useCases.GetNewsUseCase
import com.ae.domain.useCases.GetSourcesUseCase
import com.ae.news.R
import com.ae.news.common.ErrorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val getSourcesUseCase: GetSourcesUseCase,
) : ViewModel() {
    val loadingState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<ErrorState>()
    val sourcesLiveData = MutableLiveData<List<Source?>?>()
    val newsLiveData = MutableLiveData<List<News?>?>()

    fun loadSources(categoryId: String) {
        loadingState.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val sourcesList = getSourcesUseCase.invoke(categoryId)
                loadingState.postValue(false)
                sourcesLiveData.postValue(sourcesList)
            } catch (error: Exception) {
                val message = error.localizedMessage ?: R.string.wrong.toString()
                errorState.postValue(ErrorState(message) { loadSources(categoryId) })
            }
        }
    }

    fun loadNews(sourceId: String) {
        loadingState.value = true

        viewModelScope.launch {
            try {
                val newsList = getNewsUseCase.invoke(sourceId)
                newsLiveData.value = newsList
                loadingState.value = false
            } catch (error: Exception) {
                val message = error.localizedMessage ?: R.string.wrong.toString()
                errorState.value = ErrorState(message) { loadNews(sourceId) }
                loadingState.value = false
            }
        }
    }

//    private fun handleError(errorHttp: HttpException): com.ae.data.models.errorResponse.ErrorResponse? {
//        return Gson().fromJson(
//            errorHttp.response()?.errorBody()?.string(), com.ae.data.models.errorResponse.ErrorResponse::class.java
//        )
//    }

}