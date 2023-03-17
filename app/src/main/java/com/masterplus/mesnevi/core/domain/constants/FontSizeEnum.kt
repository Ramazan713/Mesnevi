package com.masterplus.mesnevi.core.domain.constants

import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.preferences.model.IEnumPrefValue
import com.masterplus.mesnevi.core.domain.util.UiText


enum class FontSizeEnum(
    val fontValue: Int,
    override val keyValue: Int,
): IMenuItemEnum, IEnumPrefValue {
    small(-3,1) {
        override val title: UiText
            get() = UiText.Resource(R.string.small)
        override val iconInfo: IconInfo?
            get() = null
    },
    medium(0,2) {
        override val title: UiText
            get() = UiText.Resource(R.string.medium)
        override val iconInfo: IconInfo?
            get() = null
    },
    aLittleLarge(3,3) {
        override val title: UiText
            get() = UiText.Resource(R.string.a_little_large)
        override val iconInfo: IconInfo?
            get() = null
    },
    large(7,4) {
        override val title: UiText
            get() = UiText.Resource(R.string.large)
        override val iconInfo: IconInfo?
            get() = null
    },
    veryLarge(13,5) {
        override val title: UiText
            get() = UiText.Resource(R.string.very_large)
        override val iconInfo: IconInfo?
            get() = null
    };

    companion object{
        val defaultValue = medium

        fun fromKeyValue(keyValue: Int): FontSizeEnum {
            return when(keyValue){
                1-> small
                2-> medium
                3-> aLittleLarge
                4-> large
                5-> veryLarge
                else -> defaultValue
            }
        }
    }
}