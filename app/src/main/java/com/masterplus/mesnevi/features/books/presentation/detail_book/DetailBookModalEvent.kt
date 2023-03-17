package com.masterplus.mesnevi.features.books.presentation.detail_book

import com.masterplus.mesnevi.core.domain.model.ContentModel


sealed interface DetailBookModalEvent {

    data class ShowSelectBottomMenu(val contentModel: ContentModel, val pos: Int): DetailBookModalEvent

    object SelectFontSize: DetailBookModalEvent
}
