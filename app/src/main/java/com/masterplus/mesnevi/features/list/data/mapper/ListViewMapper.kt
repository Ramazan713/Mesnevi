package com.masterplus.mesnevi.features.list.data.mapper

import com.masterplus.mesnevi.features.list.data.local.ListViewEntity
import com.masterplus.mesnevi.features.list.domain.model.ListModel
import com.masterplus.mesnevi.features.list.domain.model.ListView

fun ListViewEntity.toListView(): ListView{
    return ListView(
        id = id,
        name = name,
        isRemovable = isRemovable,
        isArchive = isArchive,
        listPos = listPos,
        contentMaxPos = contentMaxPos,
        itemCounts = itemCounts
    )
}

fun ListView.toListViewEntity(): ListViewEntity{
    return ListViewEntity(
        id = id,
        name = name,
        isRemovable = isRemovable,
        isArchive = isArchive,
        listPos = listPos,
        contentMaxPos = contentMaxPos,
        itemCounts = itemCounts
    )
}

fun ListView.toListModel(): ListModel {
    return ListModel(
        id = id,
        name = name,
        isRemovable = isRemovable,
        isArchive = isArchive,
        pos = listPos
    )
}





