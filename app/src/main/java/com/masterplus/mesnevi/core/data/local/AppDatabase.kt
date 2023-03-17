package com.masterplus.mesnevi.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.masterplus.mesnevi.core.data.local.entity.*
import com.masterplus.mesnevi.core.data.local.service.ContentDao
import com.masterplus.mesnevi.features.books.data.services.TitleContentsNoteDao
import com.masterplus.mesnevi.features.savepoint.data.SavePointDao
import com.masterplus.mesnevi.features.savepoint.data.entity.SavePointEntity
import com.masterplus.mesnevi.features.search.data.entity.ContentFtsEntity
import com.masterplus.mesnevi.features.books.data.entity.HistoryEntity
import com.masterplus.mesnevi.features.books.data.services.HistoryDao
import com.masterplus.mesnevi.features.search.data.service.SearchDao
import com.masterplus.mesnevi.features.books.data.entity.BookEntity
import com.masterplus.mesnevi.features.books.data.entity.TitleEntity
import com.masterplus.mesnevi.features.books.data.entity.TitleNoteCrossRef
import com.masterplus.mesnevi.features.books.data.services.BookDao
import com.masterplus.mesnevi.features.list.data.local.ListContentsEntity
import com.masterplus.mesnevi.features.list.data.local.ListEntity
import com.masterplus.mesnevi.features.list.data.local.ListViewEntity
import com.masterplus.mesnevi.features.list.data.services.ListContentsDao
import com.masterplus.mesnevi.features.list.data.services.ListDao
import com.masterplus.mesnevi.features.list.data.services.ListViewDao
import com.masterplus.mesnevi.features.settings.data.local.entity.BackupMetaEntity
import com.masterplus.mesnevi.features.settings.data.local.service.BackupMetaDao
import com.masterplus.mesnevi.features.settings.data.local.service.LocalBackupDao

@Database(
    version = 1,
    entities = [BookEntity::class,ContentEntity::class,NoteEntity::class,
        TitleEntity::class, HistoryEntity::class, ContentFtsEntity::class,
        SavePointEntity::class, ListContentsEntity::class, ListEntity::class,
        BackupMetaEntity::class, ContentNotesCrossRef::class, TitleNoteCrossRef::class
    ],
    views = [ListViewEntity::class],
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun bookDao(): BookDao
    abstract fun contentDao(): ContentDao
    abstract fun titleDao(): TitleContentsNoteDao
    abstract fun historyDao(): HistoryDao
    abstract fun searchDao(): SearchDao
    abstract fun savePointDao(): SavePointDao
    abstract fun listDao(): ListDao
    abstract fun listViewDao(): ListViewDao
    abstract fun listContentsDao(): ListContentsDao
    abstract fun backupMetaDao(): BackupMetaDao
    abstract fun localBackupDao(): LocalBackupDao
}