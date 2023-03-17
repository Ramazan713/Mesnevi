package com.masterplus.mesnevi.features.list.presentation.archive_list

import com.masterplus.mesnevi.features.list.domain.model.ListView

sealed class ArchiveListModalEvent{
    data class ShowSelectBottomMenu(val listView: ListView): ArchiveListModalEvent()

}
