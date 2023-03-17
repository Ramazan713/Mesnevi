package com.masterplus.mesnevi.features.settings.presentation


sealed class SettingModalEvent {

    object SelectFontSize: SettingModalEvent()

    object BackupSectionInit: SettingModalEvent()
}