package com.masterplus.mesnevi.core.data.local.service

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ContentDao {
    @Transaction
    @Query("""
        select T.bookId from Contents C, Titles T where
        C.titleId = T.id and C.id = :contentId 
    """)
    suspend fun getBookIdFromContentId(contentId: Int): Int?

    @Transaction
    @Query("""
        select count(*) from contents C, titles T where C.titleId = T.id and T.bookId = :bookId and C.id < :contentId
    """)
    suspend fun getPosFromBookId(bookId: Int, contentId: Int): Int
}