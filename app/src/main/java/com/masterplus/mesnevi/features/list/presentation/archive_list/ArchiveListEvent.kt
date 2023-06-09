package com.masterplus.mesnevi.features.list.presentation.archive_list

import com.masterplus.mesnevi.features.list.domain.model.ListView

sealed class ArchiveListEvent{
    data class ShowDialog(val showDialog: Boolean,
                          val dialogEvent: ArchiveListDialogEvent? = null): ArchiveListEvent()

    data class ShowModal(val showModal: Boolean,
                         val modalEvent: ArchiveListModalEvent? = null): ArchiveListEvent()


    data class Rename(val listView: ListView, val newName: String): ArchiveListEvent()


    data class UnArchive(val listView: ListView): ArchiveListEvent()

    data class Delete(val listView: ListView): ArchiveListEvent()
}
