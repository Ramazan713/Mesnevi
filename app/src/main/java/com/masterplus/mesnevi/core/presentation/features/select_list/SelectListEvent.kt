package com.masterplus.mesnevi.core.presentation.features.select_list

import com.masterplus.mesnevi.features.list.domain.model.ListView

sealed class SelectListEvent{
    data class LoadData(val contentId: Int): SelectListEvent()

    data class ShowModal(val showModal: Boolean,
                          val modalEvent: SelectListModalEvent? = null): SelectListEvent()

    data class NewList(val listName: String): SelectListEvent()

    data class AddToFavorite(val contentId: Int): SelectListEvent()

    data class AddToList(val contentId: Int, val listView: ListView): SelectListEvent()
}
