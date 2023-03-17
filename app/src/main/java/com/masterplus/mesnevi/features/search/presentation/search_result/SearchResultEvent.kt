package com.masterplus.mesnevi.features.search.presentation.search_result

import com.masterplus.mesnevi.core.domain.constants.FontSizeEnum
import com.masterplus.mesnevi.core.domain.constants.ShareItemEnum
import com.masterplus.mesnevi.core.domain.model.ContentModel

sealed class SearchResultEvent{
    data class Search(val query: String,val criteriaValue: Int,val scrollPos: Int):
        SearchResultEvent()

    object ClearSearch: SearchResultEvent()

    data class ShowBookNavigateClick(val contentModel: ContentModel): SearchResultEvent()

    data class ShowModal(val showModal: Boolean,
                         val modalEvent: SearchResultModalEvent? = null): SearchResultEvent()

    data class ShowDialog(val showDialog: Boolean,
                          val dialogEvent: SearchResultDialogEvent? = null): SearchResultEvent()

    data class Share(val contentModel: ContentModel,
                     val shareType: ShareItemEnum,
    ): SearchResultEvent()

    data class CopyClipboard(val contentModel: ContentModel): SearchResultEvent()

    data class SetFontSize(val fontSize: FontSizeEnum): SearchResultEvent()
}
