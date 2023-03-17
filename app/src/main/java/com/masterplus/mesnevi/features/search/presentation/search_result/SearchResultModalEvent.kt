package com.masterplus.mesnevi.features.search.presentation.search_result

import com.masterplus.mesnevi.core.domain.model.ContentModel

sealed class SearchResultModalEvent{
    data class ShowSelectBottomMenu(val contentModel: ContentModel,val pos: Int): SearchResultModalEvent()

    object SelectFontSize: SearchResultModalEvent()
}
