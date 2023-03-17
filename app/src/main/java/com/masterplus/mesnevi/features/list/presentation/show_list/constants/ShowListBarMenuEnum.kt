package com.masterplus.mesnevi.features.list.presentation.show_list.constants

import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.constants.IMenuItemEnum
import com.masterplus.mesnevi.core.domain.constants.IconInfo
import com.masterplus.mesnevi.core.domain.util.UiText

enum class ShowListBarMenuEnum: IMenuItemEnum {
    showSelectSavePoint {
        override val title: UiText
            get() = UiText.Resource(R.string.save_point)
        override val iconInfo: IconInfo
            get() = IconInfo(drawableId = R.drawable.ic_baseline_save_24)
    }
}