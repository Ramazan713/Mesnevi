package com.masterplus.mesnevi.features.list.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.masterplus.mesnevi.core.data.local.entity.ContentEntity


@Entity(
    tableName = "ListContents",
    foreignKeys = [
        ForeignKey(
            entity = ContentEntity::class,
            parentColumns = ["id"],
            childColumns = ["contentId"]
        ),
        ForeignKey(
            entity = ListEntity::class,
            parentColumns = ["id"],
            childColumns = ["listId"]
        )
    ]
)
data class ListContentsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val listId: Int,
    val contentId: Int,
    val pos: Int
)
