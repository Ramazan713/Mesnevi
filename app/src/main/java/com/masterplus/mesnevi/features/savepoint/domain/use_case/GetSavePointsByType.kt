package com.masterplus.mesnevi.features.savepoint.domain.use_case

import android.util.Log
import com.masterplus.mesnevi.features.savepoint.domain.model.SavePoint
import com.masterplus.mesnevi.features.savepoint.domain.repo.SavePointRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavePointsByType @Inject constructor(
    private val savePointRepo: SavePointRepo
){

    operator fun invoke(typeIds: List<Int>): Flow<List<SavePoint>>{
        return savePointRepo.getSavePointsFlow(typeIds)
    }

    operator fun invoke(typeId: Int,saveKey: String?): Flow<List<SavePoint>>{
        if(saveKey!=null){
            return savePointRepo.getSavePointsFlowBySaveKey(typeId, saveKey)
        }
        return savePointRepo.getSavePointsFlow(listOf(typeId))
    }
}