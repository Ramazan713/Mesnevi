package com.masterplus.mesnevi.features.list.domain.repo

import com.masterplus.mesnevi.core.domain.model.ContentNotes
import com.masterplus.mesnevi.features.list.domain.model.ListContents
import kotlinx.coroutines.flow.Flow

interface ListContentsRepo {

    suspend fun insertListContent(listContents: ListContents)

    suspend fun updateListContent(listContents: ListContents)

    suspend fun deleteListContent(listContents: ListContents)

    suspend fun getListContent(contentId: Int, listId: Int): ListContents?

    suspend fun insertListContents(listContents: List<ListContents>)

    suspend fun getListContentsByListId(listId: Int): List<ListContents>

    fun hasContentInFavoriteList(contentId: Int): Flow<Boolean>

    fun hasContentInRemovableList(contentId: Int): Flow<Boolean>

    suspend fun deleteListContentsByListId(listId: Int)

    fun getContentListsByListId(listId: Int): Flow<List<ContentNotes>>


}