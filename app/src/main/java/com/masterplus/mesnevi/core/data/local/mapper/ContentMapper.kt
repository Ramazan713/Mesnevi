package com.masterplus.mesnevi.core.data.local.mapper

import com.masterplus.mesnevi.core.data.local.entity.ContentEntity
import com.masterplus.mesnevi.core.data.local.entity.ContentNotesEntity
import com.masterplus.mesnevi.core.data.local.entity.NoteEntity
import com.masterplus.mesnevi.core.domain.model.ContentModel
import com.masterplus.mesnevi.core.domain.model.ContentNotes
import com.masterplus.mesnevi.core.domain.model.NoteModel

fun ContentEntity.toContent(): ContentModel {
    return ContentModel(
        id = id,
        content = content,
        itemNumber = itemNumber,
    )
}


fun ContentNotesEntity.toContentNotes(): ContentNotes {
    return ContentNotes(
        contentModel = contentEntity.toContent(),
        notes = notes.map { it.toNote() }
    )
}



fun NoteEntity.toNote(): NoteModel {
    return NoteModel(
        id = id,
        content = content,
    )
}
