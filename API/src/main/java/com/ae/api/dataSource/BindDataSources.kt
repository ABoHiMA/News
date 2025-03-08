package com.ae.api.dataSource

import com.ae.data.dataSource.NewsOnlineDataSource
import com.ae.data.dataSource.SourcesOnlineDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class BindDataSources {

    @Binds
    @ViewModelScoped
    abstract fun bindNewsOnlineDataSource(newsOnlineSourceImpl: NewsOnlineSourceImpl): NewsOnlineDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindSourcesOnlineDataSource(sourcesOnlineSourceImpl: SourcesOnlineSourceImpl): SourcesOnlineDataSource

}