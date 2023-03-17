package com.masterplus.mesnevi.features.savepoint.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SavePoints")
data class SavePointEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val title: String,
    val itemPosIndex: Int,
    val type: Int,
    val saveKey: String,
    val modifiedTimestamp: Long,
    val autoType: Int
)
