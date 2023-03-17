package com.masterplus.mesnevi.core.domain.util

sealed class UiEvent{
    data class ShowMessage(val message: UiText): UiEvent()
}
