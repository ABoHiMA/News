package com.ae.api.dataSource

import com.ae.api.models.news.NewsDTO
import com.ae.api.webServices.WebServices
import com.ae.data.dataSource.NewsOnlineDataSource
import com.ae.domain.models.News
import javax.inject.Inject

class NewsOnlineSourceImpl @Inject constructor(private val webServices: WebServices) :
    NewsOnlineDataSource {

    override suspend fun getNews(sourceId: String): List<News> {
        val response = webServices.getNews(sourceId)
        val newsList =
            response.articles?.filterNotNull()?.map { newsDTO: NewsDTO -> newsDTO.toNews() }
                ?.toList()
        return newsList ?: listOf()
    }
}