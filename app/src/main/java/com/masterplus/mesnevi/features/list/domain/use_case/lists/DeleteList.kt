package com.masterplus.mesnevi.features.list.domain.use_case.lists

import com.masterplus.mesnevi.features.list.data.mapper.toListModel
import com.masterplus.mesnevi.features.list.domain.model.ListView
import com.masterplus.mesnevi.features.list.domain.repo.ListContentsRepo
import com.masterplus.mesnevi.features.list.domain.repo.ListRepo
import javax.inject.Inject

class DeleteList @Inject constructor(
    private val listRepo: ListRepo,
    private val listContentsRepo: ListContentsRepo
){

    suspend fun invoke(listView: ListView){
        listContentsRepo.deleteListContentsByListId(listView.id?:0)
        listRepo.deleteList(listView.toListModel())
    }
}