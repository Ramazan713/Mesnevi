package com.masterplus.mesnevi.features.settings.data.repo

import com.google.firebase.storage.FirebaseStorage
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.data.K
import com.masterplus.mesnevi.core.domain.util.Resource
import com.masterplus.mesnevi.core.domain.util.UiText
import com.masterplus.mesnevi.features.settings.domain.model.User
import com.masterplus.mesnevi.features.settings.data.mapper.toBackupMeta
import com.masterplus.mesnevi.features.settings.domain.repo.StorageService
import com.masterplus.mesnevi.features.settings.domain.model.BackupMeta
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(
    private val storage: FirebaseStorage
): StorageService {

    private val rootRef = storage.getReference("Backups/")



    override suspend fun getFiles(user: User): Resource<List<BackupMeta>>{
        val ref = rootRef.child(user.uid)
        val backupMetas = mutableListOf<BackupMeta>()
        try {
            val results = ref.listAll().await()
            results.items.forEach { item->
                val metadata = item.metadata.await()
                backupMetas.add(metadata.toBackupMeta())
            }
        }catch (e: Exception){
            return Resource.Error(getExceptionText(e))
        }
        return Resource.Success(backupMetas)
    }

    override suspend fun getFileData(user: User, fileName: String): Resource<ByteArray>{
        return try {
            val ref = rootRef.child(user.uid).child(fileName)
            val result = ref.getBytes(K.maxDownloadSizeBytes).await()
            Resource.Success(result)
        }catch (e: Exception){
            Resource.Error(getExceptionText(e))
        }
    }

    override suspend fun deleteFile(user: User, fileName: String): Resource<Unit>{
        val ref = rootRef.child(user.uid).child(fileName)
        return try {
            ref.delete().await()
            Resource.Success(Unit)
        }catch (e: Exception){
            Resource.Error(getExceptionText(e))
        }
    }

    override suspend fun uploadData(user: User, fileName: String, data: ByteArray): Resource<Unit>{
       return try {
           val ref = rootRef.child(user.uid).child(fileName)
           ref.putBytes(data).await()
           Resource.Success(Unit)
       }catch (e: Exception){
           Resource.Error(getExceptionText(e))
       }
    }

    private fun getExceptionText(e: Exception): UiText{
        return e.localizedMessage?.let { UiText.Text(it) } ?: UiText.Resource(R.string.error)
    }
}