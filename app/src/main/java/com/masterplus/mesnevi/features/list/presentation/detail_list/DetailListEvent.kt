package com.masterplus.mesnevi.features.list.presentation.detail_list

import com.masterplus.mesnevi.core.domain.constants.FontSizeEnum
import com.masterplus.mesnevi.core.domain.constants.ShareItemEnum
import com.masterplus.mesnevi.core.domain.model.ContentModel
import com.masterplus.mesnevi.features.books.presentation.detail_book.DetailBookEvent

sealed class DetailListEvent{
    data class LoadData(val listId: Int,val pos: Int): DetailListEvent()

    data class ShowBookNavigateClick(val contentModel: ContentModel): DetailListEvent()

    data class ShowModal(val showModal: Boolean,
                         val modalEvent: DetailListModalEvent? = null): DetailListEvent()

    data class ShowDialog(val showDialog: Boolean,
                          val dialogEvent: DetailListDialogEvent? = null): DetailListEvent()

    data class Share(val contentModel: ContentModel,
                     val shareType: ShareItemEnum,
    ): DetailListEvent()

    data class CopyClipboard(val contentModel: ContentModel): DetailListEvent()

    data class SetFontSize(val fontSize: FontSizeEnum): DetailListEvent()
}
