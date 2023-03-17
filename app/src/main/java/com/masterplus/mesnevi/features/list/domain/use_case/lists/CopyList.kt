package com.masterplus.mesnevi.features.list.domain.use_case.lists

import android.util.Log
import com.masterplus.mesnevi.features.list.data.mapper.toListModel
import com.masterplus.mesnevi.features.list.domain.model.ListView
import com.masterplus.mesnevi.features.list.domain.repo.ListContentsRepo
import com.masterplus.mesnevi.features.list.domain.repo.ListRepo
import javax.inject.Inject

class CopyList @Inject constructor(
    private val listRepo: ListRepo,
    private val listContentsRepo: ListContentsRepo
){

    suspend fun invoke(listView: ListView){
        val pos = listRepo.getMaxPos() + 1

        val listModel = listView.toListModel()
            .copy(
                pos = pos,
                id = null,
                isRemovable = true,
                name = listView.name + " Copy"
            )

        val newListId = listRepo.insertList(listModel).toInt()

        val listContents = listContentsRepo.getListContentsByListId(listView.id?:0)
        val newListContents = listContents.map {
            it.copy(id = null, listId = newListId)
        }
        listContentsRepo.insertListContents(newListContents)
    }
}