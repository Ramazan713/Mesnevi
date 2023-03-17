package com.masterplus.mesnevi.features.settings.domain.model

data class BackupMeta(
    val id: Int?,
    val fileName: String,
    val updatedDate: Long,
    val title: String = ""
)
