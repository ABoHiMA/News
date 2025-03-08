package com.ae.domain.repositories

import com.ae.domain.models.News

interface NewsRepository {
    suspend fun getNews(sourceId: String): List<News>
}