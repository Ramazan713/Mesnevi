package com.masterplus.mesnevi.core.domain.constants

import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.util.UiText

enum class ShareItemEnum: IMenuItemEnum {
    shareText {
        override val title: UiText
            get() = UiText.Resource(R.string.share_text)
        override val iconInfo: IconInfo
            get() = IconInfo(R.drawable.ic_baseline_text_format_24)
    },
    shareLink {
        override val title: UiText
            get() = UiText.Resource(R.string.share_link)
        override val iconInfo: IconInfo
            get() = IconInfo(R.drawable.ic_baseline_link_24)
    }
}