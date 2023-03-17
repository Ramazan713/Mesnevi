package com.masterplus.mesnevi.features.list.presentation.archive_list

import com.masterplus.mesnevi.features.list.domain.model.ListView

data class ArchiveListState(
    val items: List<ListView> = emptyList(),
    val showDialog: Boolean = false,
    val dialogEvent: ArchiveListDialogEvent? = null,
    val showModal: Boolean = false,
    val modalEvent: ArchiveListModalEvent? = null
)
