package com.masterplus.mesnevi.features.settings.presentation.backup_select

import com.masterplus.mesnevi.features.settings.domain.model.BackupMeta

data class SelectBackupState(
    val items: List<BackupMeta> = emptyList(),
    val selectedItem: BackupMeta? = null,
    val isRefreshEnabled: Boolean = true,
    val refreshSeconds: Int = 0,
    val isLoading: Boolean = false,
    val showDialog: Boolean = false,
    val dialogEvent: BackupSelectDialogEvent? = null
)
