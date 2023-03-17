package com.masterplus.mesnevi.features.books.data.mapper

import com.masterplus.mesnevi.features.books.data.entity.HistoryEntity
import com.masterplus.mesnevi.features.books.domain.model.History

fun HistoryEntity.toHistory(): History {
    return History(
        id = id,
        content = content,
        timeStamp = timeStamp
    )
}

fun History.toHistoryEntity(): HistoryEntity {
    return HistoryEntity(
        id = id,
        content = content,
        timeStamp = timeStamp
    )
}