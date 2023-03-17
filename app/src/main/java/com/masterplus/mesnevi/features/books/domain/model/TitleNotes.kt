package com.masterplus.mesnevi.features.books.domain.model

import com.masterplus.mesnevi.core.domain.model.NoteModel

data class TitleNotes(
    val title: TitleModel,
    val notes: List<NoteModel>
)
