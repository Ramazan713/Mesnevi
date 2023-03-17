package com.masterplus.mesnevi.features.books.data.services

import androidx.room.*
import com.masterplus.mesnevi.features.books.data.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(historyEntity: HistoryEntity)

    @Query("""select * from histories order by timestamp desc""")
    fun getFlowHistories(): Flow<List<HistoryEntity>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateHistory(historyEntity: HistoryEntity)

    @Query("""select * from histories where lower(content) = lower(:query)""")
    suspend fun getHistoryByQuery(query: String): HistoryEntity?

    @Delete
    suspend fun deleteHistory(historyEntity: HistoryEntity)


    @Query("""delete from histories""")
    suspend fun deleteAllHistory()
}