package com.masterplus.mesnevi.features.books.presentation.show_books

import com.masterplus.mesnevi.features.search.domain.model.SearchCriteria

sealed class ShowBookUiEvent{
    data class NavigateToResult(val query: String, val criteria: SearchCriteria): ShowBookUiEvent()
}
