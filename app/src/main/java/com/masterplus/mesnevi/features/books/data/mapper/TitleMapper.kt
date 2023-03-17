package com.masterplus.mesnevi.core.data.local.mapper

import com.masterplus.mesnevi.features.books.data.entity.TitleContentNotesEntity
import com.masterplus.mesnevi.features.books.data.entity.TitleEntity
import com.masterplus.mesnevi.features.books.data.entity.TitleNotesEntity
import com.masterplus.mesnevi.features.books.domain.model.TitleContentNotes
import com.masterplus.mesnevi.features.books.domain.model.TitleModel
import com.masterplus.mesnevi.features.books.domain.model.TitleNotes


fun TitleEntity.toTitle(): TitleModel {
    return TitleModel(
        id = id,
        content = content,
    )
}

fun TitleNotesEntity.toTitleNotes(): TitleNotes {
    return TitleNotes(
        title = titleEntity.toTitle(),
        notes = notes.map { it.toNote() }
    )
}

fun TitleContentNotesEntity.toTitleContentNotes(): TitleContentNotes {
    return TitleContentNotes(
        titleNotes = titleNotes.toTitleNotes(),
        contentNotes = contentNotes.map { it.toContentNotes() }
    )
}
