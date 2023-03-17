package com.masterplus.mesnevi.features.books.data.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.masterplus.mesnevi.core.data.local.entity.ContentEntity
import com.masterplus.mesnevi.core.data.local.entity.ContentNotesEntity


data class TitleContentNotesEntity(
    @Embedded
    val titleNotes: TitleNotesEntity,
    @Relation(
        entity = ContentEntity::class,
        parentColumn = "id",
        entityColumn = "titleId",
    )
    val contentNotes: List<ContentNotesEntity>
)
