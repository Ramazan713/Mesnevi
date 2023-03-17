package com.masterplus.mesnevi.features.books.data.repo

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import com.masterplus.mesnevi.features.books.data.entity.HistoryEntity
import com.masterplus.mesnevi.features.books.data.mapper.toHistory
import com.masterplus.mesnevi.features.books.data.mapper.toHistoryEntity
import com.masterplus.mesnevi.features.books.data.services.HistoryDao
import com.masterplus.mesnevi.features.books.domain.model.History
import com.masterplus.mesnevi.features.books.domain.repo.HistoryRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

class HistoryRepoImpl @Inject constructor(
    private val historyDao: HistoryDao
): HistoryRepo {

    override fun getFlowHistories(): Flow<List<History>> {
        return historyDao.getFlowHistories().map { items->items.map { it.toHistory() } }
    }

    override suspend fun insertOrUpdateHistory(query: String) {
        val history = historyDao.getHistoryByQuery(query.toLowerCase(Locale.current))
        val timeStamp = Date().time
        val updatedHistory: HistoryEntity = if(history!=null){
            HistoryEntity(id = history.id, content = query, timeStamp = timeStamp)
        }else{
            HistoryEntity(content = query, timeStamp = timeStamp, id = null)
        }
        historyDao.insertHistory(updatedHistory)
    }

    override suspend fun deleteHistories() {
        historyDao.deleteAllHistory()
    }

    override suspend fun deleteHistory(history: History) {
        historyDao.deleteHistory(history.toHistoryEntity())
    }
}