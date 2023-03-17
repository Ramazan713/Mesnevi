package com.masterplus.mesnevi.features.savepoint.presentation.select_savepoint

sealed class SelectSavePointUiEvent{
    data class NavigateToDetailBook(val bookId: Int, val pos: Int): SelectSavePointUiEvent()

    data class NavigateToSearch(val query: String,val criteriaValue: Int,val pos: Int): SelectSavePointUiEvent()

    data class NavigateToList(val listId: Int, val pos: Int): SelectSavePointUiEvent()
}
