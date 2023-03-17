package com.masterplus.mesnevi.features.list.domain.use_case.lists

import com.masterplus.mesnevi.features.list.domain.model.ListModel
import com.masterplus.mesnevi.features.list.domain.repo.ListRepo
import javax.inject.Inject

class InsertList @Inject constructor(
    private val listRepo: ListRepo
) {

    suspend fun invoke(listName: String){
        val pos = listRepo.getMaxPos() + 1
        val listModel = ListModel(
            name = listName,
            pos = pos,
            isRemovable = true,
            isArchive = false,
            id = null
        )
        listRepo.insertList(listModel)
    }
}