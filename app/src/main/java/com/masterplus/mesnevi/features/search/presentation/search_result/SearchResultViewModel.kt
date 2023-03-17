package com.masterplus.mesnevi.features.search.presentation.search_result

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.masterplus.mesnevi.core.domain.model.ContentModel
import com.masterplus.mesnevi.core.domain.model.ContentNotes
import com.masterplus.mesnevi.core.domain.preferences.AppPreferences
import com.masterplus.mesnevi.core.domain.repo.ContentRepo
import com.masterplus.mesnevi.features.list.presentation.detail_list.DetailListEvent
import com.masterplus.mesnevi.features.list.presentation.detail_list.DetailListUiEvent
import com.masterplus.mesnevi.features.search.domain.model.SearchCriteria
import com.masterplus.mesnevi.features.search.domain.repo.SearchRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val searchRepo: SearchRepo,
    private val contentRepo: ContentRepo,
    private val appPreferences: AppPreferences
): ViewModel() {

    var state by mutableStateOf(SearchResultState())
        private set

    private var _searchUiEvent = Channel<SearchResultUiEvent>()
    val searchUiEvent = _searchUiEvent.receiveAsFlow()

    fun onEvent(event: SearchResultEvent){
        when(event){
            is SearchResultEvent.Search -> {
                viewModelScope.launch {
                    state = state.copy(isLoading = true)
                    val criteria = SearchCriteria.fromKeyValue(event.criteriaValue)
                    val fontSize = appPreferences.getEnumItem(AppPreferences.fontSizeEnum)
                    val items = searchRepo.searchContents(event.query,criteria)
                    state = state.copy(
                        isLoading = false, items = items,
                        criteria = criteria, query = event.query,
                        showClearIcon = items.isNotEmpty(),
                        fontSize = fontSize
                    )
                    delay(300)
                    _searchUiEvent.send(SearchResultUiEvent.ScrollTo(event.scrollPos))
                }
            }
            is SearchResultEvent.ClearSearch -> {
                val items = state.items.map { content->
                    val newContent = content.contentModel.copy(
                        content = clearContent(content.contentModel),
                    )
                    content.copy(contentModel = newContent)
                }
                state = state.copy(items = items, showClearIcon = false)
            }
            is SearchResultEvent.ShowBookNavigateClick -> {
                viewModelScope.launch {
                    val contentId = event.contentModel.id ?: return@launch
                    val bookId = contentRepo.getBookIdFromContentId(contentId) ?: return@launch
                    val pos = contentRepo.getPosFromBookId(bookId, contentId)
                    _searchUiEvent.send(SearchResultUiEvent.NavigateToShowBook(bookId,pos))
                }
            }
            is SearchResultEvent.ShowModal -> {
                state = state.copy(
                    showModal = event.showModal,
                    modalEvent = event.modalEvent
                )
            }
            is SearchResultEvent.ShowDialog -> {
                state = state.copy(
                    showDialog = event.showDialog,
                    dialogEvent = event.dialogEvent
                )
            }
            is SearchResultEvent.SetFontSize -> {
                appPreferences.setEnumItem(AppPreferences.fontSizeEnum,event.fontSize)
                state = state.copy(
                    fontSize = event.fontSize
                )
            }
            is SearchResultEvent.CopyClipboard -> {
                viewModelScope.launch {
                    val bookId = contentRepo.getBookIdFromContentId(event.contentModel.id?:0) ?: return@launch
                    _searchUiEvent.send(SearchResultUiEvent.CopyClipboard(event.contentModel,bookId))
                }
            }
            is SearchResultEvent.Share -> {
                viewModelScope.launch {
                    val bookId = contentRepo.getBookIdFromContentId(event.contentModel.id?:0) ?: return@launch
                    val pos = contentRepo.getPosFromBookId(bookId,event.contentModel.id?:0)
                    _searchUiEvent.send(SearchResultUiEvent.ShareText(event.contentModel,bookId,event.shareType,pos))
                }
            }
        }
    }

    private fun clearContent(contentModel: ContentModel): String{
        return contentModel.content
            .replace("<b>","")
            .replace("</b>","")
    }

}