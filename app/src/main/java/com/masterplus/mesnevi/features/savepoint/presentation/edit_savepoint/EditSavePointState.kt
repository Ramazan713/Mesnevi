package com.masterplus.mesnevi.features.savepoint.presentation.edit_savepoint

import com.masterplus.mesnevi.features.savepoint.domain.model.SavePoint

data class EditSavePointState(
    val savePoints: List<SavePoint> = emptyList(),
    val selectedSavePoint: SavePoint? = null,
    val showDialog: Boolean = false,
    val dialogEvent: EditSavePointDialogEvent? = null
)
