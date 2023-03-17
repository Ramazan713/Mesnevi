package com.masterplus.mesnevi.features.list.domain.use_case.list_contents

import com.masterplus.mesnevi.features.list.domain.model.SelectableListView
import com.masterplus.mesnevi.features.list.domain.repo.ListViewRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetSelectableLists @Inject constructor(
    private val listViewRepo: ListViewRepo
) {
    operator fun invoke(useArchiveAsList: Boolean?, contentId: Int): Flow<List<SelectableListView>>{
        val removableLists = listViewRepo.getRemovableListViews(
            if(useArchiveAsList==true)null else false,
        )
        val selectedLists = listViewRepo.getListViewsByContentId(contentId)

        return combine(removableLists,selectedLists){lists,selecteds->
            lists.map { item->
                val isSelected = selecteds.contains(item)
                SelectableListView(item,isSelected)
            }
        }
    }

}