package com.masterplus.mesnevi.features.settings.data.local.repo

import android.util.Log
import com.google.gson.reflect.TypeToken
import com.masterplus.mesnevi.core.data.local.TransactionProvider
import com.masterplus.mesnevi.core.domain.preferences.AppPreferences
import com.masterplus.mesnevi.features.list.domain.use_case.lists.ListUseCases
import com.masterplus.mesnevi.features.settings.data.local.service.LocalBackupDao
import com.masterplus.mesnevi.features.settings.data.mapper.*
import com.masterplus.mesnevi.features.settings.domain.JsonParser
import com.masterplus.mesnevi.features.settings.domain.model.HistoryBackup
import com.masterplus.mesnevi.features.settings.domain.model.ListBackup
import com.masterplus.mesnevi.features.settings.domain.model.ListContentsBackup
import com.masterplus.mesnevi.features.settings.domain.model.SavePointBackup
import com.masterplus.mesnevi.features.settings.domain.repo.LocalBackupRepo
import java.lang.reflect.Type
import javax.inject.Inject

class LocalBackupRepoImpl @Inject constructor(
    private val backupDao: LocalBackupDao,
    private val jsonParser: JsonParser,
    private val appPreferences: AppPreferences,
    private val transactionProvider: TransactionProvider
): LocalBackupRepo {

    override suspend fun getJsonData(): String{
        val histories = backupDao.getHistories().map { it.toHistoryBackup() }
        val lists = backupDao.getLists().map { it.toListBackup() }
        val listContents = backupDao.getListContents().map { it.toListContentsBackup() }
        val savePoints = backupDao.getSavePoints().map { it.toSavePointBackup() }

        val preferencesDict = appPreferences.toDict()

        val resultMap = mapOf(
            "histories" to jsonParser.toJson(histories),
            "lists" to jsonParser.toJson(lists),
            "listContents" to jsonParser.toJson(listContents),
            "savePoints" to jsonParser.toJson(savePoints),
            "appPreferences" to jsonParser.toJson(preferencesDict),
        )
        return jsonParser.toJson(resultMap)
    }


    override suspend fun fromJsonData(data: String, removeAllData: Boolean, addOnLocalData: Boolean){
        val dataMap = jsonParser.fromJson<Map<String,Any>>(data,TypeToken.get(Map::class.java).type)

        val historiesJson = dataMap["histories"]?.toString() ?: return
        val listsJson = dataMap["lists"]?.toString() ?: return
        val listContentsJson = dataMap["listContents"]?.toString()  ?: return
        val savePointsJson = dataMap["savePoints"]?.toString() ?: return
        val appPreferencesJson = dataMap["appPreferences"]?.toString() ?: return

        val histories = jsonParser.fromJson<List<HistoryBackup>>(historiesJson,getListType<HistoryBackup>())
        val lists = jsonParser.fromJson<List<ListBackup>>(listsJson,getListType<ListBackup>())
        val listContents = jsonParser.fromJson<List<ListContentsBackup>>(listContentsJson,getListType<ListContentsBackup>())
        val savePoints = jsonParser.fromJson<List<SavePointBackup>>(savePointsJson,getListType<SavePointBackup>())
        val appPreferencesMap = jsonParser.fromJson<Map<String, Any>>(appPreferencesJson,TypeToken.get(Map::class.java).type)

        transactionProvider.runAsTransaction {
            if(removeAllData){
                backupDao.deleteUserData()
            }
            backupDao.insertHistories(histories.map { it.toHistoryEntity() })

            if(addOnLocalData){
                val clearedListContentsEntity = listContents.map { it.copy(id = null).toListContentsEntity() }
                var pos = backupDao.getListMaxPos() + 1
                lists.forEach { list->
                    val newListId = backupDao.insertList(list.copy(id = null, isRemovable = true, pos = pos).toListEntity()).toInt()
                    pos += 1
                    val updatedListContents = clearedListContentsEntity.filter { it.listId == list.id }
                        .map { it.copy(listId = newListId) }
                    backupDao.insertListContents(updatedListContents)
                }
            }else{
                backupDao.insertLists(lists.map { it.toListEntity() })
                backupDao.insertListContents(listContents.map { it.toListContentsEntity() })
            }
            backupDao.insertSavePoints(savePoints.map { it.toSavePointEntity() })
        }
        appPreferences.fromDict(appPreferencesMap)
    }

    override suspend fun deleteAllLocalUserData() {
        backupDao.deleteUserData()
    }

    private inline fun <reified T> getListType(): Type {
        return object: TypeToken<List<T>>(){}.type
    }

}