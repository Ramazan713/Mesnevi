package com.masterplus.mesnevi.features.search.domain.repo

import androidx.paging.PagingData
import com.masterplus.mesnevi.core.domain.model.ContentNotes
import com.masterplus.mesnevi.features.search.domain.model.SearchCriteria
import kotlinx.coroutines.flow.Flow

interface SearchRepo {

    suspend fun searchContents(query: String, criteria: SearchCriteria): List<ContentNotes>
}