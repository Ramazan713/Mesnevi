package com.masterplus.mesnevi.features.savepoint.presentation.select_savepoint

import com.masterplus.mesnevi.features.savepoint.domain.model.SavePoint

sealed class SelectSavePointDialogEvent{

    data class AskDelete(val savePoint: SavePoint): SelectSavePointDialogEvent()
    data class EditTitle(val savePoint: SavePoint): SelectSavePointDialogEvent()
}
