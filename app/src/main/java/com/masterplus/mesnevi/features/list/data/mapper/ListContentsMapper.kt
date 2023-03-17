package com.masterplus.mesnevi.features.list.data.mapper

import com.masterplus.mesnevi.features.list.data.local.ListContentsEntity
import com.masterplus.mesnevi.features.list.domain.model.ListContents

fun ListContentsEntity.toListContents(): ListContents{
    return ListContents(
        id = id,
        listId = listId,
        contentId = contentId,
        pos = pos
    )
}

fun ListContents.toListContentsEntity(): ListContentsEntity{
    return ListContentsEntity(
        id = id,
        listId = listId,
        contentId = contentId,
        pos = pos
    )
}
