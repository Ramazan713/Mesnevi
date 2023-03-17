package com.masterplus.mesnevi.features.settings.presentation.backup_select

import com.masterplus.mesnevi.features.settings.domain.model.BackupMeta

sealed class SelectBackupEvent{
    object Refresh: SelectBackupEvent()

    data class SelectItem(val backupMeta: BackupMeta): SelectBackupEvent()

    object OverrideBackup: SelectBackupEvent()

    object AddTopOfBackup: SelectBackupEvent()

    data class ShowDialog(val showDialog: Boolean, val dialogEvent: BackupSelectDialogEvent? = null ): SelectBackupEvent()
}
