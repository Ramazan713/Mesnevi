package com.masterplus.mesnevi.features.settings.data.local.service

import androidx.room.*
import com.masterplus.mesnevi.features.books.data.entity.HistoryEntity
import com.masterplus.mesnevi.features.list.data.local.ListContentsEntity
import com.masterplus.mesnevi.features.list.data.local.ListEntity
import com.masterplus.mesnevi.features.savepoint.data.entity.SavePointEntity

@Dao
interface LocalBackupDao {

    @Query("""select * from histories""")
    suspend fun getHistories(): List<HistoryEntity>

    @Query("""select * from lists""")
    suspend fun getLists(): List<ListEntity>

    @Query("""select * from listContents""")
    suspend fun getListContents(): List<ListContentsEntity>

    @Query("""select * from savePoints""")
    suspend fun getSavePoints(): List<SavePointEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistories(histories: List<HistoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLists(lists: List<ListEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListContents(listContents: List<ListContentsEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavePoints(savePoints: List<SavePointEntity>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: ListEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListContent(listContent: ListContentsEntity): Long


    @Query("""delete from savePoints""")
    suspend fun deleteSavePointsWithQuery()

    @Query("""delete from lists""")
    suspend fun deleteListsWithQuery()

    @Query("""delete from listContents""")
    suspend fun deleteListContentsWithQuery()

    @Query("""delete from histories""")
    suspend fun deleteHistoriesWithQuery()


    @Query("""select ifnull(max(pos),0) from lists""")
    suspend fun getListMaxPos(): Int


    @Transaction
    suspend fun deleteUserData(){
        deleteListContentsWithQuery()
        deleteSavePointsWithQuery()
        deleteListsWithQuery()
        deleteHistoriesWithQuery()
    }

}