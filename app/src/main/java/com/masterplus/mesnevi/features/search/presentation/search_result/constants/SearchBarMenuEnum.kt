package com.masterplus.mesnevi.features.search.presentation.search_result.constants

import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.constants.IMenuItemEnum
import com.masterplus.mesnevi.core.domain.constants.IconInfo
import com.masterplus.mesnevi.core.domain.util.UiText

enum class SearchBarMenuEnum: IMenuItemEnum {
    clearSearchQuery{
        override val title: UiText
            get() = UiText.Resource(R.string.clear_search_query)

        override val iconInfo: IconInfo
            get() = IconInfo(drawableId = R.drawable.ic_baseline_clear_all_24)

    },
    selectFontSize{
        override val title: UiText
            get() = UiText.Resource(R.string.font_size)
        override val iconInfo: IconInfo
            get() = IconInfo(R.drawable.ic_baseline_font_download_24)

    },
    editBookBottom{
        override val title: UiText
            get() = UiText.Resource(R.string.add_savepoint)

        override val iconInfo: IconInfo
            get() = IconInfo(drawableId = R.drawable.ic_baseline_book_24)
    },

}