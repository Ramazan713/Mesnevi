package com.masterplus.mesnevi.core.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.masterplus.mesnevi.core.domain.model.NoteModel

@Entity(
    tableName = "Notes",
)
data class NoteEntity(
    @PrimaryKey
    val id: Int?,
    val content: String,
)
