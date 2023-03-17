package com.masterplus.mesnevi.features.books.data.repo


import com.masterplus.mesnevi.core.data.local.mapper.toTitleContentNotes
import com.masterplus.mesnevi.features.books.data.services.TitleContentsNoteDao
import com.masterplus.mesnevi.features.books.domain.repo.TitleContentsNoteRepo
import com.masterplus.mesnevi.features.books.presentation.detail_book.model.TitleContentDetailModel
import javax.inject.Inject

class TitleContentsNoteRepoImpl @Inject constructor(
    private val titleContentsDao: TitleContentsNoteDao
): TitleContentsNoteRepo {

    override suspend fun getTitleContentNotesByBookId(bookId: Int): List<TitleContentDetailModel> {

        return titleContentsDao.getTitleContentNotesByBookId(bookId)
            .map { it.toTitleContentNotes() }
            .let { TitleContentDetailModel.fromTitleContentNotesList(it) }
    }

}