package com.masterplus.mesnevi.features.list.presentation.detail_list

import com.masterplus.mesnevi.core.domain.model.ContentModel


sealed class DetailListDialogEvent{
    data class EditSavePoint(val pos: Int? = null): DetailListDialogEvent()

    object ShowSelectNumber : DetailListDialogEvent()

    data class Share(val contentModel: ContentModel): DetailListDialogEvent()
}
