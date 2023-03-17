package com.masterplus.mesnevi.features.list.data.services

import androidx.room.*
import com.masterplus.mesnevi.core.data.local.entity.ContentNotesEntity
import com.masterplus.mesnevi.features.list.data.local.ListEntity

@Dao
interface ListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(listEntity: ListEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateList(listEntity: ListEntity)

    @Delete
    suspend fun deleteList(listEntity: ListEntity)

    @Query("""select ifnull(max(pos),0) from lists""")
    suspend fun getMaxPos(): Int

    @Query("""select * from lists where id = :listId""")
    suspend fun getListById(listId: Int): ListEntity?


}