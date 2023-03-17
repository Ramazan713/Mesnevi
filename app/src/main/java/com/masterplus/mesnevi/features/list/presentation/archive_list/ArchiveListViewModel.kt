package com.masterplus.mesnevi.features.list.presentation.archive_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.util.UiEvent
import com.masterplus.mesnevi.core.domain.util.UiText
import com.masterplus.mesnevi.features.list.domain.use_case.lists.ListUseCases
import com.masterplus.mesnevi.features.list.presentation.show_list.ShowListEvent
import com.masterplus.mesnevi.features.list.presentation.show_list.ShowListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchiveListViewModel @Inject constructor(
    private val listUseCases: ListUseCases
): ViewModel(){


    var state by mutableStateOf(ArchiveListState())
        private set

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var loadDataJob: Job? = null

    init {
        loadData()
    }

    fun onEvent(event: ArchiveListEvent){
        when(event){
            is ArchiveListEvent.ShowDialog -> {
                state = state.copy(
                    showDialog = event.showDialog,
                    dialogEvent = event.dialogEvent
                )
            }
            is ArchiveListEvent.ShowModal -> {
                state = state.copy(
                    showModal = event.showModal,
                    modalEvent = event.modalEvent
                )
            }
            is ArchiveListEvent.UnArchive -> {
                viewModelScope.launch {
                    listUseCases.updateList.invoke(event.listView, newIsArchive = false)
                    _uiEvent.send(UiEvent.ShowMessage(UiText.Resource(R.string.successfully_unarchive)))
                }
            }

            is ArchiveListEvent.Delete -> {
                viewModelScope.launch {
                    listUseCases.deleteList.invoke(event.listView)
                    _uiEvent.send(UiEvent.ShowMessage(UiText.Resource(R.string.successfully_deleted)))
                }
            }
            is ArchiveListEvent.Rename -> {
                viewModelScope.launch {
                    listUseCases.updateList.invoke(event.listView, newName = event.newName)
                    _uiEvent.send(UiEvent.ShowMessage(UiText.Resource(R.string.success)))
                }
            }
        }
    }


    private fun loadData(){
        loadDataJob?.cancel()
        loadDataJob = viewModelScope.launch {
            listUseCases.getLists.invoke(true).collectLatest {items->
                state = state.copy(
                    items = items
                )
            }
        }
    }

}