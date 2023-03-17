package com.masterplus.mesnevi.core.domain.preferences

import com.masterplus.mesnevi.core.domain.constants.FontSizeEnum
import com.masterplus.mesnevi.core.domain.preferences.model.EnumPrefKey
import com.masterplus.mesnevi.core.domain.preferences.model.IEnumPrefValue
import com.masterplus.mesnevi.core.domain.preferences.model.PrefKey
import com.masterplus.mesnevi.features.search.domain.model.SearchCriteria
import com.masterplus.mesnevi.core.domain.constants.ThemeEnum
import java.util.Date

interface AppPreferences {

    fun <T>setItem(item: PrefKey<T>, value: T)

    fun<T>getItem(item: PrefKey<T>): T

    fun <E:Enum<E>> setEnumItem(criteria: EnumPrefKey<E>,value: IEnumPrefValue)

    fun <E:Enum<E>> getEnumItem(criteria: EnumPrefKey<E>): E

    fun clear()

    fun toDict(): Map<String,Any>

    fun fromDict(map: Map<String,Any>)

    companion object{
        val searchCriteria = EnumPrefKey("searchCriteria",
            SearchCriteria.defaultValue, SearchCriteria::fromKeyValue)

        val themeEnum = EnumPrefKey("themeEnum",
            ThemeEnum.defaultValue, ThemeEnum::fromKeyValue)

        val fontSizeEnum = EnumPrefKey("fontSize",
            FontSizeEnum.defaultValue,FontSizeEnum::fromKeyValue)

        val themeDynamic = PrefKey("themeDynamic",false)
        val useArchiveLikeList = PrefKey("useArchiveLikeList",false)
        val backupMetaCounter = PrefKey("backupMetaCounter",0L)
        val showBackupSectionForLogin = PrefKey("showBackupSectionForLogin",true)

        val prefValues = listOf<PrefKey<Any>>(themeDynamic, useArchiveLikeList, showBackupSectionForLogin)
        val prefEnumValues = listOf(searchCriteria,themeEnum,fontSizeEnum)
    }
}