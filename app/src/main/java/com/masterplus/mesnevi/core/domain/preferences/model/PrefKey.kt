package com.masterplus.mesnevi.core.domain.preferences.model

data class PrefKey<out T>(
    val key: String,
    val default: T,
)