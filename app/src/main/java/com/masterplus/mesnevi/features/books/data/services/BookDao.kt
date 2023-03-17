package com.masterplus.mesnevi.features.books.data.services

import androidx.room.Dao
import androidx.room.Query
import com.masterplus.mesnevi.features.books.data.entity.BookEntity

@Dao
interface BookDao {

    @Query("select * from books")
    suspend fun getBooks(): List<BookEntity>

    @Query("select * from books where id=:id")
    suspend fun getBookById(id: Int): BookEntity?
}