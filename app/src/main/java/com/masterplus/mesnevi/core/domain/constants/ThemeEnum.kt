package com.masterplus.mesnevi.core.domain.constants

import android.util.Log
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.preferences.model.IEnumPrefValue
import com.masterplus.mesnevi.core.domain.util.UiText

enum class ThemeEnum(
    override val title: UiText,
    override val keyValue: Int
): IEnumPrefValue, IMenuItemEnum {
    system(
        title = UiText.Resource(R.string.system),
        keyValue = 1
    ) {
        override fun hasDarkTheme(useSystemDark: Boolean): Boolean {
            return useSystemDark
        }

        override val iconInfo: IconInfo?
            get() = null
    },
    light(
        title = UiText.Resource(R.string.light),
        keyValue = 2
    ) {
        override fun hasDarkTheme(useSystemDark: Boolean): Boolean {
            return false
        }
        override val iconInfo: IconInfo?
            get() = null
    },
    dark(
        title = UiText.Resource(R.string.dark),
        keyValue = 3
    ) {
        override fun hasDarkTheme(useSystemDark: Boolean): Boolean {
            return true
        }
        override val iconInfo: IconInfo?
            get() = null
    };

    abstract fun hasDarkTheme(useSystemDark: Boolean):Boolean

    companion object{

        val defaultValue = system

        fun fromKeyValue(keyValue: Int): ThemeEnum {
            return when(keyValue){
                1-> system
                2-> light
                3-> dark
                else -> defaultValue
            }
        }
    }
}