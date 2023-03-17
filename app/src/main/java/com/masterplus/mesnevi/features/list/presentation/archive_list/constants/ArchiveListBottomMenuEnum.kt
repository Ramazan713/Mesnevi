package com.masterplus.mesnevi.features.list.presentation.archive_list.constants

import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.constants.IMenuItemEnum
import com.masterplus.mesnevi.core.domain.constants.IconInfo
import com.masterplus.mesnevi.core.domain.util.UiText

enum class ArchiveListBottomMenuEnum: IMenuItemEnum {

    rename {
        override val title: UiText
            get() = UiText.Resource(R.string.rename)

        override val iconInfo: IconInfo
            get() = IconInfo(drawableId = R.drawable.ic_baseline_drive_file_rename_outline_24)

    },
    delete {
        override val title: UiText
            get() = UiText.Resource(R.string.delete)

        override val iconInfo: IconInfo
            get() = IconInfo(drawableId = R.drawable.ic_baseline_folder_delete_24)

    },
    unArchive{
        override val title: UiText
            get() = UiText.Resource(R.string.unarchive)

        override val iconInfo: IconInfo
            get() = IconInfo(drawableId = R.drawable.ic_baseline_unarchive_24)

    },
}