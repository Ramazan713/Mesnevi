package com.masterplus.mesnevi.features.books.presentation.detail_book.constants

import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.constants.IMenuItemEnum
import com.masterplus.mesnevi.core.domain.constants.IconInfo
import com.masterplus.mesnevi.core.domain.util.UiText


enum class DetailBookBottomMenuEnum: IMenuItemEnum {
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
    editBookBottom{
        override val title: UiText
            get() = UiText.Resource(R.string.add_savepoint)
        override val iconInfo: IconInfo
            get() = IconInfo(drawableId = R.drawable.ic_baseline_book_24)
    }
}
