package com.ae.data.repos

import com.ae.data.dataSource.NewsOnlineDataSource
import com.ae.domain.models.News
import com.ae.domain.repositories.NewsRepository
import javax.inject.Inject

class NewsRepoImpl @Inject constructor(private val newsOnlineDataSource: NewsOnlineDataSource) :
    NewsRepository {
    override suspend fun getNews(sourceId: String): List<News> {
        val news = newsOnlineDataSource.getNews(sourceId)
        return news
    }
}