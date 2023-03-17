package com.masterplus.mesnevi.features.savepoint.presentation.select_savepoint

import com.masterplus.mesnevi.features.savepoint.domain.model.SavePoint
import com.masterplus.mesnevi.features.savepoint.presentation.select_savepoint.constants.SelectSavePointMenuItem

sealed class SelectSavePointEvent{
    data class LoadData(val typeIds: List<Int>): SelectSavePointEvent()

    data class Delete(val savePoint: SavePoint): SelectSavePointEvent()

    data class EditTitle(val title: String, val savePoint: SavePoint): SelectSavePointEvent()

    data class Select(val savePoint: SavePoint?): SelectSavePointEvent()

    object LoadSavePoint: SelectSavePointEvent()

    data class ShowDialog(val showDialog: Boolean,
                          val dialogEvent: SelectSavePointDialogEvent? = null): SelectSavePointEvent()

    data class SelectDropdownMenuItem(val item: SelectSavePointMenuItem): SelectSavePointEvent()
}
