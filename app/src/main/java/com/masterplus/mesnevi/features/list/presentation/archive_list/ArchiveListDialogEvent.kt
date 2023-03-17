package com.masterplus.mesnevi.features.list.presentation.archive_list

import com.masterplus.mesnevi.features.list.domain.model.ListView

sealed class ArchiveListDialogEvent{
    data class AskDelete(val listView: ListView): ArchiveListDialogEvent()

    data class Rename(val listView: ListView): ArchiveListDialogEvent()

    data class AskUnArchive(val listView: ListView): ArchiveListDialogEvent()
}
