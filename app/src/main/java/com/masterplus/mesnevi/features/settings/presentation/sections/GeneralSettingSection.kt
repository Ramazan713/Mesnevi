package com.masterplus.mesnevi.features.settings.presentation.sections

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.features.settings.presentation.SettingDialogEvent
import com.masterplus.mesnevi.features.settings.presentation.SettingEvent
import com.masterplus.mesnevi.features.settings.presentation.SettingModalEvent
import com.masterplus.mesnevi.features.settings.presentation.SettingState
import com.masterplus.mesnevi.features.settings.presentation.components.SettingItem
import com.masterplus.mesnevi.features.settings.presentation.components.SettingSectionItem
import com.masterplus.mesnevi.features.settings.presentation.components.SwitchItem


@Composable
fun GeneralSettingSection(
    state: SettingState,
    onEvent: (SettingEvent)->Unit,
){
    SettingSectionItem(
        title = stringResource(R.string.general_setting)
    ){
        SettingItem(
            title = stringResource(R.string.theme_mode),
            subTitle = state.themeModel.themeEnum.title.asString(),
            onClick = {
                onEvent(
                    SettingEvent.ShowDialog(true,
                    SettingDialogEvent.SelectThemeEnum))
            },
            resourceId = R.drawable.ic_baseline_palette_24
        )

        SettingItem(
            title = stringResource(R.string.search_criteria),
            subTitle = state.searchCriteria.title.asString(),
            onClick = {
                onEvent(
                    SettingEvent.ShowDialog(true,
                    SettingDialogEvent.SelectSearchCriteria))
            },
            resourceId = R.drawable.baseline_search_24
        )

        SettingItem(
            title = stringResource(R.string.font_size),
            subTitle = state.fontSize.title.asString(),
            onClick = {
                onEvent(
                    SettingEvent.ShowModal(true,
                    SettingModalEvent.SelectFontSize))
            },
            resourceId = R.drawable.ic_baseline_font_download_24
        )

        if(state.themeModel.enabledDynamicColor){
            SwitchItem(
                title = stringResource(R.string.use_dynamic_colors),
                value = state.themeModel.useDynamicColor,
                onValueChange = {onEvent(SettingEvent.SetDynamicTheme(it))}
            )
        }
    }
}
