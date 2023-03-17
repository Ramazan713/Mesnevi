package com.masterplus.mesnevi.core.domain.repo

interface ContentRepo {
    suspend fun getBookIdFromContentId(contentId: Int): Int?

    suspend fun getPosFromBookId(bookId: Int, contentId: Int): Int
}