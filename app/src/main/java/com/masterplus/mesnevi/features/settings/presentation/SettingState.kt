package com.masterplus.mesnevi.features.settings.presentation

import com.masterplus.mesnevi.core.domain.constants.FontSizeEnum
import com.masterplus.mesnevi.core.domain.model.ThemeModel
import com.masterplus.mesnevi.features.settings.domain.model.User
import com.masterplus.mesnevi.features.search.domain.model.SearchCriteria

data class SettingState(
    val themeModel: ThemeModel = ThemeModel(),
    val user: User? = null,
    val isLoading: Boolean = false,
    val searchCriteria: SearchCriteria = SearchCriteria.defaultValue,
    val fontSize: FontSizeEnum = FontSizeEnum.defaultValue,
    val useArchiveAsList: Boolean = false,
    val showDialog: Boolean = false,
    val dialogEvent: SettingDialogEvent? = null,
    val showModal: Boolean = false,
    val modalEvent: SettingModalEvent? = null,
)
