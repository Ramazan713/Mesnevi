package com.masterplus.mesnevi.features.search.presentation.search_result

import com.masterplus.mesnevi.core.domain.constants.FontSizeEnum
import com.masterplus.mesnevi.core.domain.model.ContentNotes
import com.masterplus.mesnevi.features.search.domain.model.SearchCriteria

data class SearchResultState(
    val isLoading: Boolean = false,
    val items: List<ContentNotes> = emptyList(),
    val fontSize: FontSizeEnum = FontSizeEnum.defaultValue,
    val criteria: SearchCriteria = SearchCriteria.defaultValue,
    val query: String = "",
    val showModal: Boolean = false,
    val modalEvent: SearchResultModalEvent? = null,
    val showDialog: Boolean = false,
    val dialogEvent: SearchResultDialogEvent? = null,
    val showClearIcon: Boolean = false
)
