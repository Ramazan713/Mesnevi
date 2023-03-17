package com.masterplus.mesnevi.features.list.domain.use_case.lists

import com.masterplus.mesnevi.core.domain.model.ContentNotes
import com.masterplus.mesnevi.features.list.domain.repo.ListContentsRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetContentsFromList @Inject constructor(
    private val listContentsRepo: ListContentsRepo
){

    operator fun invoke(listId: Int): Flow<List<ContentNotes>> {
        return listContentsRepo.getContentListsByListId(listId)
    }

}