package com.masterplus.mesnevi.features.list.domain.use_case.lists

import com.masterplus.mesnevi.features.list.data.mapper.toListModel
import com.masterplus.mesnevi.features.list.domain.model.ListView
import com.masterplus.mesnevi.features.list.domain.repo.ListRepo
import javax.inject.Inject

class UpdateList @Inject constructor(
    private val listRepo: ListRepo
) {

    suspend fun invoke(listView: ListView,newName: String? = null,
                       newIsArchive: Boolean? = null){
        val listModel = listView.toListModel().let {
            it.copy(name = newName ?: it.name, isArchive = newIsArchive ?: it.isArchive)
        }
        listRepo.updateList(listModel)
    }

}