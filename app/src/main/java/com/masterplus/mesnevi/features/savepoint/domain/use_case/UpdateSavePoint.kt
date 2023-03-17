package com.masterplus.mesnevi.features.savepoint.domain.use_case

import com.masterplus.mesnevi.features.savepoint.domain.model.SavePoint
import com.masterplus.mesnevi.features.savepoint.domain.repo.SavePointRepo
import java.util.Calendar
import javax.inject.Inject

class UpdateSavePoint @Inject constructor(
    private val savePointRepo: SavePointRepo
) {

    suspend operator fun invoke(savePoint: SavePoint){
        val date = Calendar.getInstance()
        val updatedSavePoint = savePoint.copy(modifiedDate = date)
        savePointRepo.updateSavePoint(updatedSavePoint)
    }
}