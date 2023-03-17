package com.masterplus.mesnevi.features.search.domain.di

import com.masterplus.mesnevi.core.data.local.AppDatabase
import com.masterplus.mesnevi.features.books.data.repo.HistoryRepoImpl
import com.masterplus.mesnevi.features.search.data.repo.SearchRepoImpl
import com.masterplus.mesnevi.features.books.domain.repo.HistoryRepo
import com.masterplus.mesnevi.features.search.domain.repo.SearchRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HistoryModule {

    @Provides
    @Singleton
    fun provideSearchRepo(db: AppDatabase): SearchRepo =
        SearchRepoImpl(db.searchDao())
}