package com.masterplus.mesnevi.features.list.presentation.show_list

import com.masterplus.mesnevi.features.list.domain.model.ListView


data class ShowListState(
    val items: List<ListView> = emptyList(),
    val showDialog: Boolean = false,
    val dialogEvent: ShowListDialogEvent? = null,
    val showModal: Boolean = false,
    val modalEvent: ShowListModelEvent? = null
)
