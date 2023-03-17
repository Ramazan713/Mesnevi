package com.masterplus.mesnevi.features.settings.presentation

import androidx.activity.result.ActivityResult
import com.masterplus.mesnevi.core.domain.constants.FontSizeEnum
import com.masterplus.mesnevi.core.domain.constants.ThemeEnum
import com.masterplus.mesnevi.features.search.domain.model.SearchCriteria

sealed class SettingEvent {

    object LoadData: SettingEvent()

    data class SetThemeEnum(val themeEnum: ThemeEnum): SettingEvent()

    data class SetDynamicTheme(val useDynamic: Boolean): SettingEvent()

    data class SetSearchCriteria(val criteria: SearchCriteria): SettingEvent()

    data class ShowDialog(val showDialog: Boolean,
                          val dialogEvent: SettingDialogEvent? = null): SettingEvent()

    object ResetDefaultValues: SettingEvent()

    data class UseArchiveAsList(val useArchiveAsList: Boolean): SettingEvent()

    data class ShowModal(val showModal: Boolean,
                         val modalEvent: SettingModalEvent? = null): SettingEvent()

    data class SetFontSize(val fontSize: FontSizeEnum): SettingEvent()

    object SignInLaunch: SettingEvent()

    data class SignInWithGoogle(val activityResult: ActivityResult): SettingEvent()

    data class SignOut(val backupBeforeSignOut: Boolean): SettingEvent()

    object LoadLastBackup: SettingEvent()

    object NotShowBackupInitDialog: SettingEvent()

    object DeleteAllUserData: SettingEvent()
}
