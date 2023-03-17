package com.masterplus.mesnevi.features.books.presentation.detail_book

import com.masterplus.mesnevi.core.domain.model.ContentModel

sealed class DetailBookDialogEvent{
    data class EditSavePoint(val pos: Int? = null): DetailBookDialogEvent()

    object ShowSelectNumber: DetailBookDialogEvent()

    data class Share(val contentModel: ContentModel,val pos: Int): DetailBookDialogEvent()
}
