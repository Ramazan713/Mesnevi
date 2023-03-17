package com.masterplus.mesnevi.features.search.presentation.search_result

import com.masterplus.mesnevi.core.domain.constants.ShareItemEnum
import com.masterplus.mesnevi.core.domain.model.ContentModel

sealed class SearchResultUiEvent {
    data class NavigateToShowBook(val bookId:Int,val pos: Int): SearchResultUiEvent()

    data class ScrollTo(val pos: Int): SearchResultUiEvent()

    data class ShareText(val contentModel: ContentModel,
                         val bookId: Int,
                         val shareType: ShareItemEnum,
                         val pos: Int
    ): SearchResultUiEvent()

    data class CopyClipboard(val contentModel: ContentModel,
                             val bookId: Int,): SearchResultUiEvent()

}