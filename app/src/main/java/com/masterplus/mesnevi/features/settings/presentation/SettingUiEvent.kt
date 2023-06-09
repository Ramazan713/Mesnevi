package com.masterplus.mesnevi.features.settings.presentation

import android.content.Intent

sealed class SettingUiEvent {
    data class LaunchGoogleSignIn(val intent: Intent): SettingUiEvent()

    object RefreshApp: SettingUiEvent()
}
