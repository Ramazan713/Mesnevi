package com.masterplus.mesnevi.features.list.domain.use_case.lists

import com.masterplus.mesnevi.features.list.domain.model.ListView
import com.masterplus.mesnevi.features.list.domain.repo.ListViewRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLists @Inject constructor(
    private val listViewRepo: ListViewRepo
){
    fun invoke(isArchive: Boolean): Flow<List<ListView>> {
        return listViewRepo.getListViews(isArchive)
    }
}