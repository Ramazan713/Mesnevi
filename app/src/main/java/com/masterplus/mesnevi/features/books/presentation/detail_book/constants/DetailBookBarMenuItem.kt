package com.masterplus.mesnevi.features.books.presentation.detail_book.constants

import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.constants.IMenuItemEnum
import com.masterplus.mesnevi.core.domain.constants.IconInfo
import com.masterplus.mesnevi.core.domain.util.UiText

enum class DetailBookBarMenuItem: IMenuItemEnum {

    selectFontSize{
        override val title: UiText
            get() = UiText.Resource(R.string.font_size)
        override val iconInfo: IconInfo
            get() = IconInfo(R.drawable.ic_baseline_font_download_24)

    },
    showSelectSavePoint {
        override val title: UiText
            get() = UiText.Resource(R.string.save_point)
        override val iconInfo: IconInfo
            get() = IconInfo(drawableId = R.drawable.ic_baseline_save_24)
    }
}