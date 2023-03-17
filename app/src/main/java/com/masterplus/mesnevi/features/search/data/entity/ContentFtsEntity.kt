package com.masterplus.mesnevi.features.search.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import com.masterplus.mesnevi.core.data.local.entity.ContentEntity

@Fts4(contentEntity = ContentEntity::class)
@Entity(tableName = "ContentsFts")
data class ContentFtsEntity(
    @PrimaryKey
    @ColumnInfo("rowid")
    val rowId: Int,
    val content: String
)
