package com.masterplus.mesnevi.core.presentation.features.select_list.constants

import androidx.compose.ui.graphics.Color
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.constants.IMenuItemEnum
import com.masterplus.mesnevi.core.domain.constants.IconInfo
import com.masterplus.mesnevi.core.domain.util.UiText

enum class SelectListMenuEnum: IMenuItemEnum {
    addFavorite {
        override val title: UiText
            get() = UiText.Resource(R.string.add_favorite)

        override val iconInfo: IconInfo
            get() = IconInfo(drawableId = R.drawable.ic_baseline_favorite_24)
    },
    addedFavorite {
        override val title: UiText
            get() = UiText.Resource(R.string.delete_favorite)

        override val iconInfo: IconInfo
            get() = IconInfo(drawableId = R.drawable.ic_baseline_favorite_24,
                tintColor = Color.Red)
    },
    addList {
        override val title: UiText
            get() = UiText.Resource(R.string.add_to_list)

        override val iconInfo: IconInfo
            get() = IconInfo(drawableId = R.drawable.ic_baseline_library_add_24)
    },
    addedList {
        override val title: UiText
            get() = UiText.Resource(R.string.delete_to_list)
        override val iconInfo: IconInfo
            get() = IconInfo(drawableId = R.drawable.ic_baseline_library_add_check_24)
    }
}