package com.masterplus.mesnevi.features.books.presentation.detail_book

import com.masterplus.mesnevi.core.domain.constants.FontSizeEnum
import com.masterplus.mesnevi.core.domain.constants.ShareItemEnum
import com.masterplus.mesnevi.core.domain.model.ContentModel

sealed class DetailBookEvent{
    data class Init(val bookId: Int,val pos: Int = 0): DetailBookEvent()

    data class ShowDialog(val showDialog: Boolean,
                          val dialogEvent: DetailBookDialogEvent? = null): DetailBookEvent()

    data class ShowModal(val showModal: Boolean,
                          val modalEvent: DetailBookModalEvent? = null): DetailBookEvent()

    data class Share(val contentModel: ContentModel,
                     val shareType: ShareItemEnum,
                     val pos: Int
    ): DetailBookEvent()

    data class CopyClipboard(val contentModel: ContentModel): DetailBookEvent()

    data class SetFontSize(val fontSize: FontSizeEnum): DetailBookEvent()
}
