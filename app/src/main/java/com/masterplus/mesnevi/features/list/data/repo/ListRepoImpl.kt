package com.masterplus.mesnevi.features.list.data.repo

import com.masterplus.mesnevi.features.list.data.mapper.toListEntity
import com.masterplus.mesnevi.features.list.data.mapper.toListModel
import com.masterplus.mesnevi.features.list.data.services.ListDao
import com.masterplus.mesnevi.features.list.domain.model.ListModel
import com.masterplus.mesnevi.features.list.domain.repo.ListRepo
import javax.inject.Inject

class ListRepoImpl @Inject constructor(
    private val listDao: ListDao
): ListRepo{
    override suspend fun insertList(listModel: ListModel): Long {
        return listDao.insertList(listModel.toListEntity())
    }

    override suspend fun updateList(listModel: ListModel) {
        listDao.updateList(listModel.toListEntity())
    }

    override suspend fun deleteList(listModel: ListModel) {
        listDao.deleteList(listModel.toListEntity())
    }

    override suspend fun getMaxPos(): Int {
        return listDao.getMaxPos()
    }

    override suspend fun getListById(listId: Int): ListModel? {
        return listDao.getListById(listId)?.toListModel()
    }
}