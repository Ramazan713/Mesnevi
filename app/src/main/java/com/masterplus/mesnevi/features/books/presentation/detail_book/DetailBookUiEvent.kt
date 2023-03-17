package com.masterplus.mesnevi.features.books.presentation.detail_book

import com.masterplus.mesnevi.core.domain.constants.ShareItemEnum
import com.masterplus.mesnevi.core.domain.model.ContentModel

sealed class DetailBookUiEvent{
    data class ScrollTo(val pos: Int): DetailBookUiEvent()

    data class ShareText(val contentModel: ContentModel,
                         val bookId: Int,
                         val shareType: ShareItemEnum,
                         val pos: Int
    ): DetailBookUiEvent()

    data class CopyClipboard(val contentModel: ContentModel,
                             val bookId: Int,): DetailBookUiEvent()

}
