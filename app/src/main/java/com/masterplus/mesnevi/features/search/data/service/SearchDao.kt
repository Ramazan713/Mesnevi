package com.masterplus.mesnevi.features.search.data.service

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.masterplus.mesnevi.core.data.local.entity.ContentNotesEntity

@Dao
interface SearchDao {

    @Transaction
    @Query("""select snippet(ctfs.contentsFts) content, c.id,c.itemNumber,c.titleId from contentsfts ctfs,
            Contents c where c.id = ctfs.rowid and ctfs.content match :query""")
    suspend fun searchContents(query: String): List<ContentNotesEntity>
}