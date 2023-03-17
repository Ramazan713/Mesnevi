package com.masterplus.mesnevi.features.list.domain.use_case.list_contents

import com.masterplus.mesnevi.features.list.domain.model.ListContents
import com.masterplus.mesnevi.features.list.domain.model.ListView
import com.masterplus.mesnevi.features.list.domain.repo.ListContentsRepo
import javax.inject.Inject

class AddListContents @Inject constructor(
    private val listContentsRepo: ListContentsRepo,
) {

    suspend fun invoke(listView: ListView,contentId: Int){
        val listContents = listContentsRepo.getListContent(contentId,listView.id?:0)
        if(listContents!=null){
            listContentsRepo.deleteListContent(listContents)
        }else{
            listContentsRepo.insertListContent(ListContents(
                id = null,
                listView.id?:0,
                contentId,
                pos = listView.contentMaxPos + 1
            ))
        }
    }
}