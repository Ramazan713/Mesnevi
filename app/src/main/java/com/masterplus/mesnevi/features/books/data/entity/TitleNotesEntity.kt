package com.masterplus.mesnevi.features.books.data.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.masterplus.mesnevi.core.data.local.entity.NoteEntity

data class TitleNotesEntity(
    @Embedded
    val titleEntity: TitleEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            TitleNoteCrossRef::class,
            parentColumn = "titleId",
            entityColumn = "noteId"
        )
    )
    val notes: List<NoteEntity>
)
