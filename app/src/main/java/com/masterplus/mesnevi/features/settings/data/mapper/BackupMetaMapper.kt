package com.masterplus.mesnevi.features.settings.data.mapper

import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import com.masterplus.mesnevi.core.domain.constants.DateFormatEnum
import com.masterplus.mesnevi.core.domain.util.DateFormatHelper
import com.masterplus.mesnevi.features.settings.data.local.entity.BackupMetaEntity
import com.masterplus.mesnevi.features.settings.domain.model.BackupMeta

fun BackupMetaEntity.toBackupMeta(): BackupMeta {
    return BackupMeta(
        id = id,
        fileName = fileName,
        updatedDate = updatedDate,
        title = "Backup - ${DateFormatHelper.getReadableDate(updatedDate, DateFormatEnum.dateTimeFull)}"
    )
}

fun BackupMeta.toBackupMetaEntity(): BackupMetaEntity{
    return BackupMetaEntity(
        id = id,
        fileName = fileName,
        updatedDate = updatedDate,
    )
}

fun StorageMetadata.toBackupMeta(): BackupMeta{
    return BackupMeta(
        fileName = name?:"",
        updatedDate = updatedTimeMillis,
        id = null,
        title = "Backup - ${DateFormatHelper.getReadableDate(updatedTimeMillis, DateFormatEnum.dateTimeFull)}"
    )
}