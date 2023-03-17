package com.masterplus.mesnevi.features.list.domain.use_case.lists

data class ListUseCases(
    val insertList: InsertList,
    val updateList: UpdateList,
    val deleteList: DeleteList,
    val getLists: GetLists,
    val copyList: CopyList,
    val getContentsFromList: GetContentsFromList
)
