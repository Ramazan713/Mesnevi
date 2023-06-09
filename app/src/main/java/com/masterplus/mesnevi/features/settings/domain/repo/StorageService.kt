package com.masterplus.mesnevi.features.settings.domain.repo

import com.masterplus.mesnevi.core.domain.util.Resource
import com.masterplus.mesnevi.features.settings.domain.model.User
import com.masterplus.mesnevi.features.settings.domain.model.BackupMeta

interface StorageService {

    suspend fun getFiles(user: User): Resource<List<BackupMeta>>

    suspend fun getFileData(user: User, fileName: String): Resource<ByteArray>

    suspend fun deleteFile(user: User, fileName: String): Resource<Unit>

    suspend fun uploadData(user: User, fileName: String, data: ByteArray): Resource<Unit>
}