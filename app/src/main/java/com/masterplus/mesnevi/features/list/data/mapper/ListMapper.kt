package com.masterplus.mesnevi.features.list.data.mapper

import com.masterplus.mesnevi.features.list.data.local.ListEntity
import com.masterplus.mesnevi.features.list.domain.model.ListModel


fun ListEntity.toListModel(): ListModel{
    return ListModel(
        id = id,
        name = name,
        isArchive = isArchive,
        isRemovable = isRemovable,
        pos = pos
    )
}


fun ListModel.toListEntity(): ListEntity{
    return ListEntity(
        id = id,
        name = name,
        isArchive = isArchive,
        isRemovable = isRemovable,
        pos = pos
    )
}