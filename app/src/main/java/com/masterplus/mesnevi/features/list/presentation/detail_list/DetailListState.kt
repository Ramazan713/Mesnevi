package com.masterplus.mesnevi.features.list.presentation.detail_list

import com.masterplus.mesnevi.core.domain.constants.FontSizeEnum
import com.masterplus.mesnevi.core.domain.model.ContentNotes


data class DetailListState(
    val items: List<ContentNotes> = emptyList(),
    val fontSize: FontSizeEnum = FontSizeEnum.defaultValue,
    val listName: String = "",
    val showModal: Boolean = false,
    val modalEvent: DetailListModalEvent? = null,
    val showDialog: Boolean = false,
    val dialogEvent: DetailListDialogEvent? = null,
)
