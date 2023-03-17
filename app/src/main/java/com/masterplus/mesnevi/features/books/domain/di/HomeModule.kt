package com.masterplus.mesnevi.features.books.domain.di

import com.masterplus.mesnevi.core.data.local.AppDatabase
import com.masterplus.mesnevi.features.books.data.repo.BookRepoImpl
import com.masterplus.mesnevi.features.books.data.repo.HistoryRepoImpl
import com.masterplus.mesnevi.features.books.data.repo.TitleContentsNoteRepoImpl
import com.masterplus.mesnevi.features.books.domain.repo.BookRepo
import com.masterplus.mesnevi.features.books.domain.repo.HistoryRepo
import com.masterplus.mesnevi.features.books.domain.repo.TitleContentsNoteRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideHistoryRepo(db: AppDatabase): HistoryRepo =
        HistoryRepoImpl(db.historyDao())

    @Provides
    fun provideBookRepo(db: AppDatabase):BookRepo =
        BookRepoImpl(bookDao = db.bookDao())

    @Provides
    @Singleton
    fun provideTitleContentNotesRepo(db: AppDatabase): TitleContentsNoteRepo =
        TitleContentsNoteRepoImpl(db.titleDao())
}