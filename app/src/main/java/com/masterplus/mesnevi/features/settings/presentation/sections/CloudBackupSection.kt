package com.masterplus.mesnevi.features.settings.presentation.sections

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.features.settings.presentation.SettingDialogEvent
import com.masterplus.mesnevi.features.settings.presentation.SettingEvent
import com.masterplus.mesnevi.features.settings.presentation.components.SettingItem
import com.masterplus.mesnevi.features.settings.presentation.components.SettingSectionItem

@Composable
fun CloudBackupSection(
    onEvent: (SettingEvent)->Unit,
){
    SettingSectionItem(
        title = stringResource(R.string.backup_n),
    ){
        SettingItem(
            title = stringResource(R.string.cloud_backup),
            onClick = {onEvent(SettingEvent.ShowDialog(true,SettingDialogEvent.ShowCloudBackup))},
            resourceId = R.drawable.ic_baseline_cloud_24
        )
    }
}