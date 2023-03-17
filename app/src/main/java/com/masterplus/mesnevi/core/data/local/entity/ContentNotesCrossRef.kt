package com.masterplus.mesnevi.core.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "ContentNotes",
    foreignKeys = [
        ForeignKey(
            entity = ContentEntity::class,
            parentColumns = ["id"],
            childColumns = ["contentId"]
        ),
        ForeignKey(
            entity = NoteEntity::class,
            parentColumns = ["id"],
            childColumns = ["noteId"]
        )
    ],
    primaryKeys = ["contentId","noteId"]
)
data class ContentNotesCrossRef(
    val contentId: Int,
    val noteId: Int
)