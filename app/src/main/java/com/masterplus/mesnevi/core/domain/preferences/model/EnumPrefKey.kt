package com.masterplus.mesnevi.core.domain.preferences.model

data class EnumPrefKey<E:Enum<E>>(
    val key: String,
    val default: IEnumPrefValue,
    val from: (Int)-> E
)