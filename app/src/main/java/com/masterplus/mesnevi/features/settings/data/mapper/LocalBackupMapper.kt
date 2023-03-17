package com.masterplus.mesnevi.features.settings.data.mapper

import com.masterplus.mesnevi.features.books.data.entity.HistoryEntity
import com.masterplus.mesnevi.features.list.data.local.ListContentsEntity
import com.masterplus.mesnevi.features.list.data.local.ListEntity
import com.masterplus.mesnevi.features.savepoint.data.entity.SavePointEntity
import com.masterplus.mesnevi.features.settings.domain.model.HistoryBackup
import com.masterplus.mesnevi.features.settings.domain.model.ListBackup
import com.masterplus.mesnevi.features.settings.domain.model.ListContentsBackup
import com.masterplus.mesnevi.features.settings.domain.model.SavePointBackup

fun HistoryEntity.toHistoryBackup(): HistoryBackup{
    return HistoryBackup(
        id = id,
        content = content,
        timeStamp = timeStamp
    )
}

fun HistoryBackup.toHistoryEntity(): HistoryEntity{
    return HistoryEntity(
        id = id,
        content = content,
        timeStamp = timeStamp
    )
}



fun ListBackup.toListEntity(): ListEntity{
    return ListEntity(
        id = id,
        name = name,
        isRemovable = isRemovable,
        isArchive = isArchive,
        pos = pos
    )
}

fun ListEntity.toListBackup(): ListBackup{
    return ListBackup(
        id = id,
        name = name,
        isRemovable = isRemovable,
        isArchive = isArchive,
        pos = pos
    )
}



fun ListContentsEntity.toListContentsBackup(): ListContentsBackup {
    return ListContentsBackup(
        id = id,
        listId = listId,
        contentId = contentId,
        pos = pos
    )
}

fun ListContentsBackup.toListContentsEntity(): ListContentsEntity{
    return ListContentsEntity(
        id = id,
        listId = listId,
        contentId = contentId,
        pos = pos
    )
}




fun SavePointEntity.toSavePointBackup(): SavePointBackup{
    return SavePointBackup(
        id = id,
        title = title,
        itemPosIndex = itemPosIndex,
        type = type,
        saveKey = saveKey,
        modifiedTimestamp = modifiedTimestamp,
        autoType = autoType
    )
}

fun SavePointBackup.toSavePointEntity(): SavePointEntity{
    return SavePointEntity(
        id = id,
        title = title,
        itemPosIndex = itemPosIndex,
        type = type,
        saveKey = saveKey,
        modifiedTimestamp = modifiedTimestamp,
        autoType = autoType
    )
}