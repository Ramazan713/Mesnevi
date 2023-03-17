package com.masterplus.mesnevi.features.list.domain.repo

import com.masterplus.mesnevi.features.list.data.local.ListViewEntity
import com.masterplus.mesnevi.features.list.domain.model.ListView
import kotlinx.coroutines.flow.Flow

interface ListViewRepo {

    fun getListViews(isArchive: Boolean): Flow<List<ListView>>

    fun getRemovableListViews(isArchive: Boolean? = null): Flow<List<ListView>>

    fun getListViewsByContentId(contentId: Int): Flow<List<ListView>>

    suspend fun getFavoriteList(): ListView?

}