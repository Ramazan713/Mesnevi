package com.masterplus.mesnevi.features.list.presentation.detail_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masterplus.mesnevi.core.domain.preferences.AppPreferences
import com.masterplus.mesnevi.core.domain.repo.ContentRepo
import com.masterplus.mesnevi.features.list.domain.repo.ListRepo
import com.masterplus.mesnevi.features.list.domain.use_case.list_contents.ListContentsUseCases
import com.masterplus.mesnevi.features.list.domain.use_case.lists.ListUseCases
import com.masterplus.mesnevi.features.search.presentation.search_result.SearchResultState
import com.masterplus.mesnevi.features.search.presentation.search_result.SearchResultUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailListViewModel @Inject constructor(
    private val listUseCases: ListUseCases,
    private val contentRepo: ContentRepo,
    private val listRepo: ListRepo,
    private val appPreferences: AppPreferences
): ViewModel(){

    var state by mutableStateOf(DetailListState())
        private set

    private var _listUiEvent = Channel<DetailListUiEvent>()
    val listUiEvent = _listUiEvent.receiveAsFlow()

    private var loadDataJob: Job? = null

    fun onEvent(event: DetailListEvent){
        when(event){
            is DetailListEvent.LoadData -> {
                loadData(event)
            }
            is DetailListEvent.ShowBookNavigateClick -> {
                viewModelScope.launch {
                    val contentId = event.contentModel.id ?: return@launch
                    val bookId = contentRepo.getBookIdFromContentId(contentId) ?: return@launch
                    val pos = contentRepo.getPosFromBookId(bookId, contentId)
                    _listUiEvent.send(DetailListUiEvent.NavigateToShowBook(bookId,pos))
                }
            }
            is DetailListEvent.ShowDialog -> {
                state = state.copy(
                    showDialog = event.showDialog,
                    dialogEvent = event.dialogEvent
                )
            }
            is DetailListEvent.ShowModal -> {
                state = state.copy(
                    showModal = event.showModal,
                    modalEvent = event.modalEvent
                )
            }
            is DetailListEvent.Share -> {
                viewModelScope.launch {
                    val bookId = contentRepo.getBookIdFromContentId(event.contentModel.id?:0) ?: return@launch
                    val pos = contentRepo.getPosFromBookId(bookId,event.contentModel.id?:0)
                    _listUiEvent.send(DetailListUiEvent.ShareText(event.contentModel,bookId,event.shareType,pos))
                }
            }
            is DetailListEvent.CopyClipboard -> {
                viewModelScope.launch {
                    val bookId = contentRepo.getBookIdFromContentId(event.contentModel.id?:0) ?: return@launch
                    _listUiEvent.send(DetailListUiEvent.CopyClipboard(event.contentModel,bookId))
                }
            }
            is DetailListEvent.SetFontSize -> {
                appPreferences.setEnumItem(AppPreferences.fontSizeEnum,event.fontSize)
                state = state.copy(
                    fontSize = event.fontSize
                )
            }

        }
    }

    private fun loadData(event: DetailListEvent.LoadData){

        val fontSize = appPreferences.getEnumItem(AppPreferences.fontSizeEnum)
        state = state.copy(fontSize = fontSize)

        loadDataJob?.cancel()
        loadDataJob = viewModelScope.launch {
            val listName = listRepo.getListById(event.listId)?.name ?: ""
            state = state.copy(listName = listName)

            listUseCases.getContentsFromList.invoke(event.listId).collectLatest {
                state = state.copy(items = it)
            }
        }
    }


}