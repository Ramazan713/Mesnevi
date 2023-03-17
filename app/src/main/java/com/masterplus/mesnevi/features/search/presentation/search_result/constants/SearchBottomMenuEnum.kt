package com.masterplus.mesnevi.features.search.presentation.search_result.constants

import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.constants.IMenuItemEnum
import com.masterplus.mesnevi.core.domain.constants.IconInfo
import com.masterplus.mesnevi.core.domain.util.UiText

enum class SearchBottomMenuEnum: IMenuItemEnum {
    shareText{
        override val title: UiText
            get() = UiText.Resource(R.string.share)
        override val iconInfo: IconInfo
            get() = IconInfo(R.drawable.ic_baseline_share_24)
    },
    copyText{
        override val title: UiText
            get() = UiText.Resource(R.string.copy_content)
        override val iconInfo: IconInfo
            get() = IconInfo(R.drawable.ic_baseline_content_copy_24)
    },
    goNotebook{
        override val title: UiText
            get() = UiText.Resource(R.string.go_to_notebook)

        override val iconInfo: IconInfo
            get() = IconInfo(drawableId = R.drawable.ic_baseline_arrow_outward_24)

    },
    editBook{
        override val title: UiText
            get() = UiText.Resource(R.string.add_savepoint)

        override val iconInfo: IconInfo
            get() = IconInfo(drawableId = R.drawable.ic_baseline_book_24)
    }
}