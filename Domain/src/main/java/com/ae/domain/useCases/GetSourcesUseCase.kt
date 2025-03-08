package com.ae.domain.useCases

import com.ae.domain.models.Source
import com.ae.domain.repositories.SourcesRepository
import javax.inject.Inject

class GetSourcesUseCase @Inject constructor(private val sourceRepository: SourcesRepository) {
    suspend fun invoke(categoryId: String): List<Source> {
        return sourceRepository.getSources(categoryId)
    }
}