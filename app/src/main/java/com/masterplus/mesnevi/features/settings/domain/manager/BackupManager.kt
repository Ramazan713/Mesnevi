package com.masterplus.mesnevi.features.settings.domain.manager

import com.masterplus.mesnevi.core.domain.util.Resource
import com.masterplus.mesnevi.features.settings.domain.model.User

interface BackupManager {

    suspend fun uploadBackup(user: User): Resource<Unit>

    suspend fun downloadBackup(user: User, fileName: String, removeAllData: Boolean, addOnLocalData: Boolean): Resource<Unit>

    suspend fun refreshBackupMetas(user: User): Resource<Unit>

    suspend fun deleteAllLocalUserData(deleteBackupMeta: Boolean)

    suspend fun downloadLastBackup(user: User): Resource<Unit>

    suspend fun hasBackupMetas(): Boolean
}