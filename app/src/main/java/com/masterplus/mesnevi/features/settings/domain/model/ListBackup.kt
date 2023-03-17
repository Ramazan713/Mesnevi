package com.masterplus.mesnevi.features.settings.domain.model

data class ListBackup(
    val id: Int?,
    val name: String,
    val isRemovable: Boolean,
    val isArchive: Boolean,
    val pos: Int
)
