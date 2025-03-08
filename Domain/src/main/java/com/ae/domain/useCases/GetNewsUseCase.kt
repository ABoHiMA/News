package com.ae.domain.useCases

import com.ae.domain.models.News
import com.ae.domain.repositories.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    suspend fun invoke(sourceId: String?, query: String?): List<News> {
        return newsRepository.getNews(
            sourceId = sourceId,
            query = query
        )
    }
}