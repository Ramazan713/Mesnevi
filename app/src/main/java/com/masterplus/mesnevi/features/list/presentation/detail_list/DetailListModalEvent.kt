package com.masterplus.mesnevi.features.list.presentation.detail_list

import com.masterplus.mesnevi.core.domain.model.ContentModel

sealed class DetailListModalEvent{
    data class ShowSelectBottomMenu(val contentModel: ContentModel,val pos: Int): DetailListModalEvent()

    object SelectFontSize: DetailListModalEvent()
}
