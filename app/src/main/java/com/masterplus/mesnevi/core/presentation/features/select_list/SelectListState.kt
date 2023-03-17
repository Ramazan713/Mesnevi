package com.masterplus.mesnevi.core.presentation.features.select_list

import com.masterplus.mesnevi.features.list.domain.model.SelectableListView
import com.masterplus.mesnevi.core.presentation.features.select_list.constants.SelectListMenuEnum

data class SelectListState(
    val items: List<SelectableListView> = emptyList(),
    val listMenuItems: List<SelectListMenuEnum> = emptyList(),
    val isFavorite: Boolean = false,
    val isAddedToList: Boolean = false,
    val showModal: Boolean = false,
    val modalEvent: SelectListModalEvent? = null
)
