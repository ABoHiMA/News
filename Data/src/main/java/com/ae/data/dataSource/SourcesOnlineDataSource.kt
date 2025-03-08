package com.ae.data.dataSource

import com.ae.domain.models.Source

interface SourcesOnlineDataSource {
    suspend fun getSources(categoryId: String): List<Source>
}