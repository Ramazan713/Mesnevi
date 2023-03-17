package com.masterplus.mesnevi.core.domain.di

import com.masterplus.mesnevi.core.data.local.AppDatabase
import com.masterplus.mesnevi.core.data.local.repo.ContentRepoImpl
import com.masterplus.mesnevi.core.domain.preferences.AppPreferences
import com.masterplus.mesnevi.features.books.data.repo.TitleContentsNoteRepoImpl
import com.masterplus.mesnevi.core.domain.repo.ContentRepo
import com.masterplus.mesnevi.features.books.domain.repo.TitleContentsNoteRepo
import com.masterplus.mesnevi.core.domain.repo.ThemeRepo
import com.masterplus.mesnevi.core.data.repo.ThemeRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideContentRepo(db: AppDatabase): ContentRepo =
        ContentRepoImpl(contentDao = db.contentDao())


    @Provides
    @Singleton
    fun provideThemeRepo(appPreferences: AppPreferences): ThemeRepo =
        ThemeRepoImpl(appPreferences)

}