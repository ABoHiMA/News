package com.ae.domain.repositories

import com.ae.domain.models.Source

interface SourcesRepository {
    suspend fun getSources(categoryId: String): List<Source>
}