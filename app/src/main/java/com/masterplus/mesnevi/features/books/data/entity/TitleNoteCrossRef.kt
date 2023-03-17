package com.masterplus.mesnevi.features.books.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import com.masterplus.mesnevi.core.data.local.entity.NoteEntity

@Entity(
    tableName = "TitleNotes",
    foreignKeys = [
        ForeignKey(
            entity = TitleEntity::class,
            parentColumns = ["id"],
            childColumns = ["titleId"]
        ),
        ForeignKey(
            entity = NoteEntity::class,
            parentColumns = ["id"],
            childColumns = ["noteId"]
        )
    ],
    primaryKeys = ["titleId","noteId"]
)
data class TitleNoteCrossRef(
    val titleId: Int,
    val noteId: Int
)
