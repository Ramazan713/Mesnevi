package com.masterplus.mesnevi.features.savepoint.data

import androidx.room.*
import com.masterplus.mesnevi.features.savepoint.data.entity.SavePointEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavePointDao {

    @Insert
    suspend fun insertSavePoint(savePointEntity: SavePointEntity)

    @Update
    suspend fun updateSavePoint(savePointEntity: SavePointEntity)

    @Delete
    suspend fun deleteSavePoint(savePointEntity: SavePointEntity)

    @Query("""select * from savePoints where type in (:typeIds) order by modifiedTimestamp desc""")
    fun getSavePointsFlow(typeIds: List<Int>):Flow<List<SavePointEntity>>

    @Query("""select * from savePoints where type=:typeId and saveKey=:saveKey order by modifiedTimestamp desc""")
    fun getSavePointsFlowBySaveKey(typeId: Int,saveKey: String):Flow<List<SavePointEntity>>

    @Query("""select * from savePoints where id=:id""")
    suspend fun getSavePointById(id: Int):SavePointEntity?
}