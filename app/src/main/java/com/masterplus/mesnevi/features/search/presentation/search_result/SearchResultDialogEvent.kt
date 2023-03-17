package com.masterplus.mesnevi.features.search.presentation.search_result

import com.masterplus.mesnevi.core.domain.model.ContentModel


sealed class SearchResultDialogEvent{
    data class EditSavePoint(val pos: Int? = null): SearchResultDialogEvent()

    object ShowSelectNumber: SearchResultDialogEvent()

    data class Share(val contentModel: ContentModel): SearchResultDialogEvent()
}
