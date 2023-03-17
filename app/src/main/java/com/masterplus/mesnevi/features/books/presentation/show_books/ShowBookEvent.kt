package com.masterplus.mesnevi.features.books.presentation.show_books

import com.masterplus.mesnevi.features.books.domain.model.History

sealed class ShowBookEvent{

    data class ChangeQuery(val query: String): ShowBookEvent()

    data class ChangeFocus(val active: Boolean): ShowBookEvent()

    data class DeleteHistory(val history: History): ShowBookEvent()

    data class HistoryClicked(val history: History): ShowBookEvent()

    object SearchClicked: ShowBookEvent()

}
