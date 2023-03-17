package com.masterplus.mesnevi.features.books.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Books")
data class BookEntity(
    @PrimaryKey
    val id: Int?,
    val name: String,
    val description: String
)