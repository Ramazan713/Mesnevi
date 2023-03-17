package com.masterplus.mesnevi.features.list.presentation.show_list

import com.masterplus.mesnevi.features.list.domain.model.ListView

sealed class ShowListModelEvent{

    data class ShowSelectBottomMenu(val listView: ListView): ShowListModelEvent()
}
