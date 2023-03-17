package com.masterplus.mesnevi.features.savepoint.domain.use_case

data class SavePointsUseCases(
    val deleteSavePoint: DeleteSavePoint,
    val insertSavePoint: InsertSavePoint,
    val updateSavePoint: UpdateSavePoint,
    val getSavePoints: GetSavePointsByType
)
