package com.ae.data.repos

import com.ae.data.dataSource.SourcesOnlineDataSource
import com.ae.domain.models.Source
import com.ae.domain.repositories.SourcesRepository
import javax.inject.Inject

class SourceRepoImpl @Inject constructor(private val sourcesOnlineDataSource: SourcesOnlineDataSource) :
    SourcesRepository {
    override suspend fun getSources(categoryId: String): List<Source> {
        val sources = sourcesOnlineDataSource.getSources(categoryId)
        return sources
    }
}