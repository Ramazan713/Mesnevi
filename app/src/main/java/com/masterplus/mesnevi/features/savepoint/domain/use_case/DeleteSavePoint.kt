package com.masterplus.mesnevi.features.savepoint.domain.use_case

import com.masterplus.mesnevi.features.savepoint.domain.model.SavePoint
import com.masterplus.mesnevi.features.savepoint.domain.repo.SavePointRepo
import javax.inject.Inject

class DeleteSavePoint @Inject constructor(
    private val savePointRepo: SavePointRepo
) {

    suspend operator fun invoke(savePoint: SavePoint){
        savePointRepo.deleteSavePoint(savePoint)
    }
}