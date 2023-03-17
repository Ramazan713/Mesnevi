package com.masterplus.mesnevi.core.domain.repo

import com.masterplus.mesnevi.core.domain.model.ThemeModel

interface ThemeRepo {

    fun getThemeModel(): ThemeModel

    fun updateThemeModel(themeModel: ThemeModel)

    fun hasSupportedDynamicTheme(): Boolean
}