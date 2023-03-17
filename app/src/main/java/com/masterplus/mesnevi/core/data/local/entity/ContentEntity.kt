package com.masterplus.mesnevi.core.data.local.entity

import androidx.room.*
import com.masterplus.mesnevi.features.books.data.entity.TitleEntity

@Entity(
    tableName = "Contents",
    foreignKeys = [
        ForeignKey(
            entity = TitleEntity::class,
            childColumns = ["titleId"],
            parentColumns = ["id"]
        ),
    ],
)
data class ContentEntity(
    @PrimaryKey
    val id: Int?,
    val content: String,
    @ColumnInfo(defaultValue = "0")
    val itemNumber: Int,
    val titleId: Int
)
