package com.masterplus.mesnevi.features.books.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Titles",
    foreignKeys = [
        ForeignKey(
            entity = BookEntity::class,
            parentColumns = ["id"],
            childColumns = ["bookId"],
        )
    ]
)
data class TitleEntity(
    @PrimaryKey
    val id: Int?,
    val content: String,
    val bookId: Int,
)
