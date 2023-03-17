package com.masterplus.mesnevi.features.savepoint.presentation.edit_savepoint

import com.masterplus.mesnevi.features.savepoint.domain.model.SavePoint

sealed class EditSavePointDialogEvent {

    data class AskDelete(val savePoint: SavePoint): EditSavePointDialogEvent()
    data class EditTitle(val savePoint: SavePoint): EditSavePointDialogEvent()
    data class AddSavePointTitle(val title: String): EditSavePointDialogEvent()
}