package com.ae.data.dataSource

import com.ae.domain.models.News

interface NewsOnlineDataSource {
    suspend fun getNews(sourceId: String?, query: String?): List<News>
}