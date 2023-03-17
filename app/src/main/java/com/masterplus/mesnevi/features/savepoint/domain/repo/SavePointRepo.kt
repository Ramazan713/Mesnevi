package com.masterplus.mesnevi.features.savepoint.domain.repo

import com.masterplus.mesnevi.features.savepoint.domain.model.SavePoint
import kotlinx.coroutines.flow.Flow

interface SavePointRepo {

    fun getSavePointsFlow(typeIds: List<Int>): Flow<List<SavePoint>>

    fun getSavePointsFlowBySaveKey(typeId: Int,saveKey: String):Flow<List<SavePoint>>

    suspend fun insertSavePoint(savePoint: SavePoint)

    suspend fun deleteSavePoint(savePoint: SavePoint)

    suspend fun getSavePointById(id: Int):SavePoint?

    suspend fun updateSavePoint(savePoint: SavePoint)

}