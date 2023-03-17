package com.masterplus.mesnevi.features.books.domain.repo

import com.masterplus.mesnevi.features.books.presentation.detail_book.model.TitleContentDetailModel

interface TitleContentsNoteRepo {

    suspend fun getTitleContentNotesByBookId(bookId: Int): List<TitleContentDetailModel>

}