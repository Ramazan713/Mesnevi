package com.masterplus.mesnevi.features.search.data.repo

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.masterplus.mesnevi.core.data.local.mapper.toContentNotes
import com.masterplus.mesnevi.core.domain.model.ContentNotes
import com.masterplus.mesnevi.features.search.data.service.SearchDao
import com.masterplus.mesnevi.features.search.domain.repo.SearchRepo
import com.masterplus.mesnevi.features.search.domain.model.SearchCriteria
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepoImpl @Inject constructor(
    private val searchDao: SearchDao
): SearchRepo {
    override suspend fun searchContents(
        query: String,
        criteria: SearchCriteria,
    ): List<ContentNotes> {
        val matchQuery = criteria.getFTSMatchQueryExpression(query)
        return searchDao.searchContents(matchQuery)
            .map { it.toContentNotes() }
    }
}