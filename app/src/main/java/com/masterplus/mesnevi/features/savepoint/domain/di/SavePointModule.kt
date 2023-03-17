package com.masterplus.mesnevi.features.savepoint.domain.di

import com.masterplus.mesnevi.core.data.local.AppDatabase
import com.masterplus.mesnevi.features.savepoint.data.repo.SavePointRepoImpl
import com.masterplus.mesnevi.features.savepoint.domain.repo.SavePointRepo
import com.masterplus.mesnevi.features.savepoint.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SavePointModule {

    @Provides
    @Singleton
    fun provideSavePointRepo(db: AppDatabase): SavePointRepo =
        SavePointRepoImpl(db.savePointDao())

    @Provides
    @Singleton
    fun provideSavePointUseCases(savePointRepo: SavePointRepo) =
        SavePointsUseCases(
            insertSavePoint = InsertSavePoint(savePointRepo),
            updateSavePoint = UpdateSavePoint(savePointRepo),
            deleteSavePoint = DeleteSavePoint(savePointRepo),
            getSavePoints = GetSavePointsByType(savePointRepo)
        )
}