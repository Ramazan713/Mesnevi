package com.masterplus.mesnevi.features.books.presentation.detail_book

import androidx.paging.PagingData
import com.masterplus.mesnevi.core.domain.constants.FontSizeEnum
import com.masterplus.mesnevi.features.books.domain.model.Book
import com.masterplus.mesnevi.features.books.domain.model.BookInfoEnum
import com.masterplus.mesnevi.features.books.presentation.detail_book.model.TitleContentDetailModel

data class DetailBookState(
    val book: Book? = null,
    val bookInfoEnum: BookInfoEnum = BookInfoEnum.book1,
    val fontSize: FontSizeEnum = FontSizeEnum.defaultValue,
    val contents: List<TitleContentDetailModel> = emptyList(),
    val isLoading: Boolean = false,
    val showDialog: Boolean = false,
    val dialogEvent: DetailBookDialogEvent? = null,
    val showModal: Boolean = false,
    val modalEvent: DetailBookModalEvent? = null
)
