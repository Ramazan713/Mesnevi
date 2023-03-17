package com.masterplus.mesnevi.core.data.repo

import android.os.Build
import com.masterplus.mesnevi.core.domain.model.ThemeModel
import com.masterplus.mesnevi.core.domain.preferences.AppPreferences
import com.masterplus.mesnevi.core.domain.repo.ThemeRepo
import javax.inject.Inject

class ThemeRepoImpl @Inject constructor(
    private val appPreferences: AppPreferences
): ThemeRepo {
    override fun getThemeModel(): ThemeModel {
        return ThemeModel(
            themeEnum = appPreferences.getEnumItem(AppPreferences.themeEnum),
            useDynamicColor = appPreferences.getItem(AppPreferences.themeDynamic),
            enabledDynamicColor = hasSupportedDynamicTheme()
        )
    }

    override fun updateThemeModel(themeModel: ThemeModel) {
        appPreferences.setEnumItem(AppPreferences.themeEnum,themeModel.themeEnum)
        appPreferences.setItem(AppPreferences.themeDynamic,themeModel.useDynamicColor)
    }

    override fun hasSupportedDynamicTheme(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    }
}