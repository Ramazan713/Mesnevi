package com.masterplus.mesnevi.features.savepoint.domain.use_case

import com.masterplus.mesnevi.core.domain.constants.AutoType
import com.masterplus.mesnevi.features.savepoint.domain.model.SavePoint
import com.masterplus.mesnevi.core.domain.constants.SavePointType
import com.masterplus.mesnevi.features.savepoint.domain.repo.SavePointRepo
import java.util.Calendar
import javax.inject.Inject

class InsertSavePoint @Inject constructor(
    private val savePointRepo: SavePointRepo
) {
    suspend operator fun invoke(
        itemPosIndex: Int,
        savePointType: SavePointType,
        autoType: AutoType = AutoType.Default,
        shortTitle: String? = null,
        title: String? = null,
    ){
        val date = Calendar.getInstance()

        val savePoint = SavePoint(
            title = title ?: SavePoint.getTitle(
                shortTitle?:"", autoType,date
            ),
            id = null,
            itemPosIndex = itemPosIndex,
            savePointType = savePointType,
            modifiedDate = date,
            autoType = autoType
        )

        savePointRepo.insertSavePoint(savePoint)
    }
}