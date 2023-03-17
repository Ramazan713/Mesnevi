package com.masterplus.mesnevi.features.settings.presentation

sealed class SettingDialogEvent {
    object SelectThemeEnum: SettingDialogEvent()

    object SelectSearchCriteria: SettingDialogEvent()

    object AskResetDefault: SettingDialogEvent()

    object AskSignOut: SettingDialogEvent()

    object ShowCloudBackup: SettingDialogEvent()

    object ShowSelectBackup: SettingDialogEvent()

    object AskMakeBackupBeforeSignOut: SettingDialogEvent()

    object AskDeleteAllData: SettingDialogEvent()
}
