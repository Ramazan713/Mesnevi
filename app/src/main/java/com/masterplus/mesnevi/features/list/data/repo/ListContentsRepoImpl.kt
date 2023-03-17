package com.masterplus.mesnevi.features.list.data.repo

import com.masterplus.mesnevi.core.data.local.mapper.toContentNotes
import com.masterplus.mesnevi.core.domain.model.ContentNotes
import com.masterplus.mesnevi.features.list.data.mapper.toListContents
import com.masterplus.mesnevi.features.list.data.mapper.toListContentsEntity
import com.masterplus.mesnevi.features.list.data.services.ListContentsDao
import com.masterplus.mesnevi.features.list.domain.model.ListContents
import com.masterplus.mesnevi.features.list.domain.repo.ListContentsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ListContentsRepoImpl @Inject constructor(
    private val listContentsDao: ListContentsDao
): ListContentsRepo {
    override suspend fun insertListContent(listContents: ListContents) {
        listContentsDao.insertListContent(listContents.toListContentsEntity())
    }

    override suspend fun updateListContent(listContents: ListContents) {
        listContentsDao.updateListContent(listContents.toListContentsEntity())
    }

    override suspend fun deleteListContent(listContents: ListContents) {
        listContentsDao.deleteListContent(listContents.toListContentsEntity())
    }

    override suspend fun getListContent(contentId: Int, listId: Int): ListContents? {
        return listContentsDao.getListContentsEntity(contentId, listId)?.toListContents()
    }

    override suspend fun insertListContents(listContents: List<ListContents>) {
        listContentsDao.insertListContents(listContents.map { it.toListContentsEntity() })
    }

    override suspend fun getListContentsByListId(listId: Int): List<ListContents> {
        return listContentsDao.getListContentsByListId(listId).map {
            it.toListContents()
        }
    }

    override fun hasContentInFavoriteList(contentId: Int): Flow<Boolean> {
        return listContentsDao.hasContentInFavoriteList(contentId)
    }

    override fun hasContentInRemovableList(contentId: Int): Flow<Boolean> {
        return listContentsDao.hasContentInRemovableList(contentId)
    }

    override suspend fun deleteListContentsByListId(listId: Int) {
        listContentsDao.deleteListContentsByListId(listId)
    }

    override fun getContentListsByListId(listId: Int): Flow<List<ContentNotes>> {
        return listContentsDao.getContentListsByListId(listId)
            .map { items-> items.map { it.toContentNotes() } }
    }
}