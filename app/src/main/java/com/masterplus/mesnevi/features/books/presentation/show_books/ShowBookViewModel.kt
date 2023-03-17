package com.masterplus.mesnevi.features.books.presentation.show_books

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masterplus.mesnevi.core.domain.preferences.AppPreferences
import com.masterplus.mesnevi.core.domain.util.UiEvent
import com.masterplus.mesnevi.features.books.domain.repo.BookRepo
import com.masterplus.mesnevi.features.books.domain.repo.HistoryRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowBookViewModel @Inject constructor(
    private val bookRepo: BookRepo,
    private val historyRepo: HistoryRepo,
    private val appPreferences: AppPreferences
): ViewModel(){

    var state by mutableStateOf(ShowBookState())
        private set

    private var _bookUiEvent = Channel<ShowBookUiEvent>()
    val bookUiEvent = _bookUiEvent.receiveAsFlow()

    private var historyLoadJob: Job? = null

    init {
        loadBooks()
        loadHistories()
    }

    fun onEvent(event: ShowBookEvent){
        when(event){
            is ShowBookEvent.ChangeFocus -> {
                state = state.copy(
                    searchBarActive = event.active
                )
            }
            is ShowBookEvent.ChangeQuery -> {
                state = state.copy(
                    query = event.query
                )
            }
            is ShowBookEvent.DeleteHistory -> {
                viewModelScope.launch {
                    historyRepo.deleteHistory(event.history)
                }
            }
            is ShowBookEvent.HistoryClicked -> {
                searchQuery(event.history.content)
            }
            ShowBookEvent.SearchClicked -> {
                searchQuery(state.query)
            }
        }
    }

    private fun searchQuery(query: String){
        if(query.isBlank())
            return
        viewModelScope.launch {
            historyRepo.insertOrUpdateHistory(query)
            val criteria = appPreferences.getEnumItem(AppPreferences.searchCriteria)
            _bookUiEvent.send(ShowBookUiEvent.NavigateToResult(query,criteria))
            state = state.copy(query = "", searchBarActive = false)
        }
    }

    private fun loadHistories(){
        historyLoadJob?.cancel()
        historyLoadJob = viewModelScope.launch {
            historyRepo.getFlowHistories().collectLatest {histories->
                state = state.copy(histories = histories)
            }
        }
    }

    private fun loadBooks(){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val books = bookRepo.getBooks()
            state = state.copy(books = books,isLoading = false)
        }
    }
}