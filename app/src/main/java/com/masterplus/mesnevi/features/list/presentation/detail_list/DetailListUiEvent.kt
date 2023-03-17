package com.masterplus.mesnevi.features.list.presentation.detail_list

import com.masterplus.mesnevi.core.domain.constants.ShareItemEnum
import com.masterplus.mesnevi.core.domain.model.ContentModel


sealed class DetailListUiEvent {
    data class NavigateToShowBook(val bookId:Int,val pos: Int): DetailListUiEvent()

    data class ShareText(val contentModel: ContentModel,
                         val bookId: Int,
                         val shareType: ShareItemEnum,
                         val pos: Int
    ): DetailListUiEvent()

    data class CopyClipboard(val contentModel: ContentModel,
                             val bookId: Int,): DetailListUiEvent()
}