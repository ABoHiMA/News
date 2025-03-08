package com.ae.api.dataSource

import com.ae.api.models.source.SourceDTO
import com.ae.api.webServices.WebServices
import com.ae.data.dataSource.SourcesOnlineDataSource
import com.ae.domain.models.Source
import javax.inject.Inject

class SourcesOnlineSourceImpl @Inject constructor(private val webServices: WebServices) :
    SourcesOnlineDataSource {
    override suspend fun getSources(categoryId: String): List<Source> {
        val response = webServices.getSources(categoryId)
        val sourcesList =
            response.sources?.filterNotNull()?.map { sourceDTO: SourceDTO -> sourceDTO.toSources() }
                ?.toList()
        return sourcesList ?: listOf()
    }
}