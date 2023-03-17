package com.masterplus.mesnevi.features.list.presentation.show_list

import com.masterplus.mesnevi.features.list.domain.model.ListView

sealed class ShowListDialogEvent{
    data class AskDelete(val listView: ListView): ShowListDialogEvent()

    object TitleToAddList: ShowListDialogEvent()

    data class Rename(val listView: ListView): ShowListDialogEvent()

    data class AskArchive(val listView: ListView): ShowListDialogEvent()

    data class AskCopy(val listView: ListView): ShowListDialogEvent()
}
