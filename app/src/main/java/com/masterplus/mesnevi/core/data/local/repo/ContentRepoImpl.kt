package com.masterplus.mesnevi.core.data.local.repo

import com.masterplus.mesnevi.core.data.local.service.ContentDao
import com.masterplus.mesnevi.core.domain.repo.ContentRepo
import javax.inject.Inject

class ContentRepoImpl @Inject constructor(
    private val contentDao: ContentDao
): ContentRepo {
    override suspend fun getBookIdFromContentId(contentId: Int): Int? {
        return contentDao.getBookIdFromContentId(contentId)
    }

    override suspend fun getPosFromBookId(bookId: Int, contentId: Int): Int {
        return contentDao.getPosFromBookId(bookId, contentId)
    }

}