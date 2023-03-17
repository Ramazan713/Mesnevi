package com.masterplus.mesnevi.features.settings.presentation.backup_select

sealed class BackupSelectDialogEvent{

    object AskOverrideBackup: BackupSelectDialogEvent()

    object AskAddOnBackup: BackupSelectDialogEvent()
}
