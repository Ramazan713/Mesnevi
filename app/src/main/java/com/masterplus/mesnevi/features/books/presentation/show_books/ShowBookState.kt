package com.masterplus.mesnevi.features.books.presentation.show_books

import com.masterplus.mesnevi.features.books.domain.model.Book
import com.masterplus.mesnevi.features.books.domain.model.History

data class ShowBookState(
    val books: List<Book> = emptyList(),
    val histories: List<History> = emptyList(),
    val isLoading: Boolean = false,
    val query: String = "",
    val searchBarActive: Boolean = false
)
