package com.masterplus.mesnevi.core.data.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.masterplus.mesnevi.core.domain.model.ContentNotes

data class ContentNotesEntity(
    @Embedded
    val contentEntity: ContentEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            ContentNotesCrossRef::class,
            parentColumn = "contentId",
            entityColumn = "noteId"
        )
    )
    val notes: List<NoteEntity>
)