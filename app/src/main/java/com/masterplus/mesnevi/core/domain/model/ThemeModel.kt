package com.masterplus.mesnevi.core.domain.model

import com.masterplus.mesnevi.core.domain.constants.ThemeEnum

data class ThemeModel(
    val themeEnum: ThemeEnum = ThemeEnum.defaultValue,
    val useDynamicColor: Boolean = false,
    val enabledDynamicColor: Boolean = false,
)
