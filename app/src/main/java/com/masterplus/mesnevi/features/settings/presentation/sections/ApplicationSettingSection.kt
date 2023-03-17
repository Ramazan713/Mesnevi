package com.masterplus.mesnevi.features.settings.presentation.sections

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.presentation.extensions.launchPlayApp
import com.masterplus.mesnevi.core.presentation.extensions.shareApp
import com.masterplus.mesnevi.features.settings.presentation.components.SettingItem
import com.masterplus.mesnevi.features.settings.presentation.components.SettingSectionItem


@Composable
fun ApplicationSettingSection(){
    val context = LocalContext.current
    SettingSectionItem(
        title = stringResource(R.string.application)
    ){
        SettingItem(
            title = stringResource(R.string.share_app),
            onClick = {
                context.shareApp()
            },
            resourceId = R.drawable.ic_baseline_share_24
        )
        SettingItem(
            title = stringResource(R.string.rate_app),
            onClick = {
                context.launchPlayApp()
            },
            resourceId = R.drawable.ic_baseline_star_24
        )
    }
}