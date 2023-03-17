package com.masterplus.mesnevi.features.books.domain.model

import com.masterplus.mesnevi.core.domain.model.ContentNotes

data class TitleContentNotes(
    val titleNotes: TitleNotes,
    val contentNotes: List<ContentNotes>
)
