package com.masterplus.mesnevi.features.books.data.services

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.masterplus.mesnevi.features.books.data.entity.TitleContentNotesEntity

@Dao
interface TitleContentsNoteDao {

    @Transaction
    @Query("""select * from titles where bookId=:bookId""")
    suspend fun getTitleContentNotesByBookId(bookId: Int): List<TitleContentNotesEntity>
}