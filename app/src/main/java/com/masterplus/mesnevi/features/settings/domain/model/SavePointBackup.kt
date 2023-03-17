package com.masterplus.mesnevi.features.settings.domain.model

data class SavePointBackup(
    val id: Int?,
    val title: String,
    val itemPosIndex: Int,
    val type: Int,
    val saveKey: String,
    val modifiedTimestamp: Long,
    val autoType: Int
)
