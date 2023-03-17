package com.masterplus.mesnevi.features.savepoint.data.mapper

import com.masterplus.mesnevi.features.savepoint.data.entity.SavePointEntity
import com.masterplus.mesnevi.core.domain.constants.AutoType
import com.masterplus.mesnevi.features.savepoint.domain.model.SavePoint
import com.masterplus.mesnevi.core.domain.constants.SavePointType
import java.util.*


fun SavePointEntity.toSavePoint(): SavePoint {
    return SavePoint(
        id = id,
        title = title,
        itemPosIndex = itemPosIndex,
        modifiedDate = Calendar.getInstance().apply { time.time = modifiedTimestamp },
        autoType = AutoType.fromTypeId(autoType),
        savePointType = SavePointType.fromTypeId(type,saveKey) ?: SavePointType.Book(1)
    )
}

fun SavePoint.toSavePointEntity(): SavePointEntity{
    return SavePointEntity(
        id = id,
        title = title,
        itemPosIndex = itemPosIndex,
        type = savePointType.typeId,
        saveKey = savePointType.toSaveKey(),
        modifiedTimestamp = modifiedDate.time.time,
        autoType = autoType.typeId
    )
}

