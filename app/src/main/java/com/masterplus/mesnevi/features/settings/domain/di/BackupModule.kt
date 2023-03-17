package com.masterplus.mesnevi.features.settings.domain.di

import com.google.firebase.storage.FirebaseStorage
import com.masterplus.mesnevi.core.data.local.AppDatabase
import com.masterplus.mesnevi.core.data.local.TransactionProvider
import com.masterplus.mesnevi.core.domain.ConnectivityProvider
import com.masterplus.mesnevi.core.domain.preferences.AppPreferences
import com.masterplus.mesnevi.features.settings.domain.repo.AuthRepo
import com.masterplus.mesnevi.features.settings.data.manager.AuthManagerImpl
import com.masterplus.mesnevi.features.settings.data.repo.StorageServiceImpl
import com.masterplus.mesnevi.features.settings.data.manager.BackupManagerImpl
import com.masterplus.mesnevi.features.settings.data.local.repo.BackupMetaImpl
import com.masterplus.mesnevi.features.settings.data.local.repo.LocalBackupRepoImpl
import com.masterplus.mesnevi.features.settings.domain.manager.AuthManager
import com.masterplus.mesnevi.features.settings.domain.manager.BackupManager
import com.masterplus.mesnevi.features.settings.domain.JsonParser
import com.masterplus.mesnevi.features.settings.domain.repo.StorageService
import com.masterplus.mesnevi.features.settings.domain.repo.BackupMetaRepo
import com.masterplus.mesnevi.features.settings.domain.repo.LocalBackupRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BackupModule {

    @Provides
    @Singleton
    fun provideStorageService(): StorageService =
        StorageServiceImpl(
            storage = FirebaseStorage.getInstance()
        )

    @Provides
    @Singleton
    fun provideBackupMetaRepo(db: AppDatabase): BackupMetaRepo =
        BackupMetaImpl(db.backupMetaDao())

    @Provides
    @Singleton
    fun provideLocalBackupRepo(db: AppDatabase,
                               jsonParser: JsonParser,
                               appPreferences: AppPreferences,
                               transactionProvider: TransactionProvider
    ): LocalBackupRepo =
        LocalBackupRepoImpl(
            backupDao = db.localBackupDao(),
            jsonParser = jsonParser,
            appPreferences = appPreferences,
            transactionProvider = transactionProvider
        )

    @Provides
    @Singleton
    fun provideBackupManager(localBackupRepo: LocalBackupRepo,
                             backupMetaRepo: BackupMetaRepo,
                             storageService: StorageService,
                             connectivityProvider: ConnectivityProvider
    ): BackupManager =
        BackupManagerImpl(
            localBackupRepo = localBackupRepo,
            backupMetaRepo = backupMetaRepo,
            storageService = storageService,
            connectivityProvider = connectivityProvider
        )




}