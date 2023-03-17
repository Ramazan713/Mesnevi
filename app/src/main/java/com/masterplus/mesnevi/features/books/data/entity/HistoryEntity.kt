package com.masterplus.mesnevi.features.books.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Histories")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val content: String,
    val timeStamp: Long
)
