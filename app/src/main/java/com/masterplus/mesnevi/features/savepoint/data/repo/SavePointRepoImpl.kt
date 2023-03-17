package com.masterplus.mesnevi.features.savepoint.data.repo

import com.masterplus.mesnevi.features.savepoint.data.SavePointDao
import com.masterplus.mesnevi.features.savepoint.data.mapper.toSavePoint
import com.masterplus.mesnevi.features.savepoint.data.mapper.toSavePointEntity
import com.masterplus.mesnevi.features.savepoint.domain.model.SavePoint
import com.masterplus.mesnevi.features.savepoint.domain.repo.SavePointRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SavePointRepoImpl @Inject constructor(
    private val savePointDao: SavePointDao
): SavePointRepo {
    override fun getSavePointsFlow(typeIds: List<Int>): Flow<List<SavePoint>> {
        return savePointDao.getSavePointsFlow(typeIds).map { items->
            items.map { it.toSavePoint() }
        }
    }

    override fun getSavePointsFlowBySaveKey(typeId: Int, saveKey: String): Flow<List<SavePoint>> {
        return savePointDao.getSavePointsFlowBySaveKey(typeId,saveKey).map { items->
            items.map { it.toSavePoint() }
        }
    }

    override suspend fun insertSavePoint(savePoint: SavePoint) {
        savePointDao.insertSavePoint(savePoint.toSavePointEntity())
    }

    override suspend fun deleteSavePoint(savePoint: SavePoint) {
        savePointDao.deleteSavePoint(savePoint.toSavePointEntity())
    }

    override suspend fun getSavePointById(id: Int): SavePoint? {
        return savePointDao.getSavePointById(id)?.toSavePoint()
    }

    override suspend fun updateSavePoint(savePoint: SavePoint) {
        savePointDao.updateSavePoint(savePoint.toSavePointEntity())
    }
}