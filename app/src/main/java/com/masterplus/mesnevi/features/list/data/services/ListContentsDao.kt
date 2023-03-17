package com.masterplus.mesnevi.features.list.data.services

import androidx.room.*
import com.masterplus.mesnevi.core.data.local.entity.ContentNotesEntity
import com.masterplus.mesnevi.features.list.data.local.ListContentsEntity
import com.masterplus.mesnevi.features.list.domain.model.ListContents
import kotlinx.coroutines.flow.Flow

@Dao
interface ListContentsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListContent(listContentsEntity: ListContentsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListContents(listContentsEntities: List<ListContentsEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateListContent(listContentsEntity: ListContentsEntity)

    @Delete
    suspend fun deleteListContent(listContentsEntity: ListContentsEntity)

    @Query("""delete from listContents where listId = :listId""")
    suspend fun deleteListContentsByListId(listId: Int)

    @Query("""select * from listContents where 
        contentId=:contentId and listId=:listId""")
    suspend fun getListContentsEntity(contentId: Int,listId: Int): ListContentsEntity?


    @Query("""
        select * from listContents where listId=:listId
    """)
    suspend fun getListContentsByListId(listId: Int): List<ListContentsEntity>

    @Transaction
    @Query("""
        select exists(select * from listContents LC, lists L
        where LC.listId = L.id and L.isRemovable = 0 and LC.contentId = :contentId)
    """)
    fun hasContentInFavoriteList(contentId: Int): Flow<Boolean>

    @Transaction
    @Query("""
        select exists(select * from listContents LC, lists L
        where LC.listId = L.id and L.isRemovable = 1 and LC.contentId = :contentId)
    """)
    fun hasContentInRemovableList(contentId: Int): Flow<Boolean>

    @Transaction
    @Query("""
        select C.* from contents C, listContents LC 
        where C.id = LC.contentId and LC.listId = :listId
        order by LC.pos desc
    """)
    fun getContentListsByListId(listId: Int): Flow<List<ContentNotesEntity>>

}