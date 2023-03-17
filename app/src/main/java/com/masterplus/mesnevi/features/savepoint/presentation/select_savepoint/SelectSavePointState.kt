package com.masterplus.mesnevi.features.savepoint.presentation.select_savepoint

import com.masterplus.mesnevi.features.savepoint.domain.model.SavePoint
import com.masterplus.mesnevi.features.savepoint.presentation.select_savepoint.constants.SelectSavePointMenuItem

data class SelectSavePointState(
    val savePoints: List<SavePoint> = emptyList(),
    val selectedSavePoint: SavePoint? = null,
    val dropdownItems: List<SelectSavePointMenuItem> = emptyList(),
    val selectedDropdownItem: SelectSavePointMenuItem? = null,
    val showDropdownMenu: Boolean = false,
    val showDialog: Boolean = false,
    val modalDialog: SelectSavePointDialogEvent? = null
)