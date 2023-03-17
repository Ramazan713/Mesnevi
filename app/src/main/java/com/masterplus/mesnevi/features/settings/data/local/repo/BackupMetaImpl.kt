package com.masterplus.mesnevi.features.settings.data.local.repo

import android.util.Log
import com.masterplus.mesnevi.features.settings.data.local.service.BackupMetaDao
import com.masterplus.mesnevi.features.settings.data.mapper.toBackupMeta
import com.masterplus.mesnevi.features.settings.data.mapper.toBackupMetaEntity
import com.masterplus.mesnevi.features.settings.domain.model.BackupMeta
import com.masterplus.mesnevi.features.settings.domain.repo.BackupMetaRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BackupMetaImpl @Inject constructor(
    private val backupMetaDao: BackupMetaDao
): BackupMetaRepo {
    override suspend fun insertBackupMeta(backupMeta: BackupMeta) {
        backupMetaDao.insertBackupMeta(backupMeta.toBackupMetaEntity())
    }

    override suspend fun insertBackupMetas(backupMetas: List<BackupMeta>) {
        backupMetaDao.insertBackupMetas(backupMetas.map { it.toBackupMetaEntity() })
    }

    override suspend fun getLastBackupMeta(): BackupMeta? {
        return backupMetaDao.getLastBackupMeta()?.toBackupMeta()
    }

    override fun getBackupMetasFlow(): Flow<List<BackupMeta>> {
        return backupMetaDao.getBackupMetasFlow()
            .map { items-> items.map { it.toBackupMeta() } }
    }

    override suspend fun deleteBackupMetas() {
        backupMetaDao.deleteBackupMetas()
    }

    override suspend fun deleteBackupMetas(backupMetas: List<BackupMeta>) {
        backupMetaDao.deleteBackupMetas(backupMetas.map { it.toBackupMetaEntity() })
    }

    override suspend fun hasBackupMetas(): Boolean {
        return backupMetaDao.hasBackupMetas()
    }

    override suspend fun getExtraBackupMetas(offset: Int): List<BackupMeta> {
        return backupMetaDao.getExtraBackupMetas(offset).map { it.toBackupMeta() }
    }
}