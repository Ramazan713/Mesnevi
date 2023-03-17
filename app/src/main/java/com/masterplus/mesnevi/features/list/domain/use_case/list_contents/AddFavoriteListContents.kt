package com.masterplus.mesnevi.features.list.domain.use_case.list_contents

import com.masterplus.mesnevi.features.list.domain.model.ListContents
import com.masterplus.mesnevi.features.list.domain.model.ListModel
import com.masterplus.mesnevi.features.list.domain.repo.ListContentsRepo
import com.masterplus.mesnevi.features.list.domain.repo.ListRepo
import com.masterplus.mesnevi.features.list.domain.repo.ListViewRepo
import javax.inject.Inject

class AddFavoriteListContents  @Inject constructor(
    private val listContentsRepo: ListContentsRepo,
    private val listViewRepo: ListViewRepo,
    private val listRepo: ListRepo
) {

    suspend fun invoke(contentId: Int){
        val favoriteList = listViewRepo.getFavoriteList()
        if(favoriteList==null){
            val listId = listRepo.insertList(ListModel(
                id = null,
                name = "Favoriler",
                isRemovable = false,
                isArchive = false,
                pos = 1
            ))
            listContentsRepo.insertListContent(ListContents(id = null,listId.toInt(),
                contentId,1))
        }else{
            listContentsRepo.getListContent(contentId,favoriteList.id?:0)?.let { listContents ->
                listContentsRepo.deleteListContent(listContents)
            }?: kotlin.run {
                listContentsRepo.insertListContent(ListContents(id = null,favoriteList.id?:0,
                    contentId,favoriteList.contentMaxPos + 1))
            }
        }
    }
}