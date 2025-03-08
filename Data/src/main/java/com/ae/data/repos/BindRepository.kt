package com.ae.data.repos

import com.ae.domain.repositories.NewsRepository
import com.ae.domain.repositories.SourcesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class BindRepository {

    @Binds
    @ViewModelScoped
    abstract fun bindNewsRepository(newsRepoImpl: NewsRepoImpl): NewsRepository

    @Binds
    @ViewModelScoped
    abstract fun bindSourcesRepository(sourceRepoImpl: SourceRepoImpl): SourcesRepository

}