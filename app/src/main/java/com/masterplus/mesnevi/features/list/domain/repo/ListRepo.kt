package com.masterplus.mesnevi.features.list.domain.repo

import com.masterplus.mesnevi.features.list.domain.model.ListModel

interface ListRepo {

    suspend fun insertList(listModel: ListModel): Long

    suspend fun updateList(listModel: ListModel)

    suspend fun deleteList(listModel: ListModel)

    suspend fun getMaxPos(): Int

    suspend fun getListById(listId: Int): ListModel?
}