package com.masterplus.mesnevi.features.savepoint.presentation.select_savepoint

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.util.UiEvent
import com.masterplus.mesnevi.core.domain.constants.SavePointType
import com.masterplus.mesnevi.core.domain.util.UiText
import com.masterplus.mesnevi.features.savepoint.domain.use_case.SavePointsUseCases
import com.masterplus.mesnevi.features.savepoint.presentation.select_savepoint.constants.SelectSavePointMenuItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectSavePointViewModel @Inject constructor(
    private val savePointsUseCases: SavePointsUseCases
): ViewModel() {

    var state by mutableStateOf(SelectSavePointState())
        private set

    private var _savePointUiEvent = Channel<SelectSavePointUiEvent>()
    val savePointUiEvent = _savePointUiEvent.receiveAsFlow()

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val filterFlow = MutableStateFlow(SelectSavePointMenuItem.all)

    private var loadDataJob: Job? = null

    fun onEvent(event: SelectSavePointEvent){
        when(event){
            is SelectSavePointEvent.Delete -> {
                viewModelScope.launch {
                    savePointsUseCases.deleteSavePoint(event.savePoint)
                    _uiEvent.send(UiEvent.ShowMessage(UiText.Resource(R.string.successfully_deleted)))
                }
            }
            is SelectSavePointEvent.EditTitle -> {
                viewModelScope.launch {
                    savePointsUseCases.updateSavePoint(event.savePoint.copy(title = event.title))
                    _uiEvent.send(UiEvent.ShowMessage(UiText.Resource(R.string.successfully_updated)))
                }
            }
            is SelectSavePointEvent.LoadData -> {
                loadData(event)
            }
            is SelectSavePointEvent.LoadSavePoint -> {
                loadSavePoint()
            }
            is SelectSavePointEvent.Select -> {
                state = state.copy(selectedSavePoint = event.savePoint)
            }
            is SelectSavePointEvent.ShowDialog -> {
                state = state.copy(
                    showDialog = event.showDialog,
                    modalDialog = event.dialogEvent
                )
            }
            is SelectSavePointEvent.SelectDropdownMenuItem -> {
                viewModelScope.launch {
                    state = state.copy(
                        selectedDropdownItem = event.item
                    )
                    filterFlow.emit(event.item)
                }
            }
        }
    }

    private fun initDropdownState(typeIds: List<Int>){
        viewModelScope.launch {
            val menuItems = SelectSavePointMenuItem.fromTypeIds(typeIds,addAll = true)
            val useMenu = menuItems.size > 1

            state = state.copy(
                dropdownItems = menuItems,
                showDropdownMenu = useMenu,
                selectedDropdownItem = menuItems.firstOrNull()
            )
            menuItems.firstOrNull()?.let { filterFlow.emit(it) }
        }
    }

    private fun loadData(event: SelectSavePointEvent.LoadData){
        initDropdownState(event.typeIds)

        loadDataJob?.cancel()
        loadDataJob = viewModelScope.launch {

            combine(filterFlow,savePointsUseCases.getSavePoints(event.typeIds)){filter,savePoints->
                if(!state.showDropdownMenu) return@combine savePoints

                if(filter == SelectSavePointMenuItem.all)
                    return@combine savePoints
                savePoints.filter { filter.typeId == it.savePointType.typeId }
            }.collectLatest {items->
                state = state.copy(
                    savePoints = items
                )
            }
        }
    }

    private fun loadSavePoint(){
        viewModelScope.launch {
            state.selectedSavePoint?.let { savePoint ->
                when(val type = savePoint.savePointType){
                    is SavePointType.Book->{
                        val bookId = type.bookId
                        savePoint.itemPosIndex
                        _savePointUiEvent.send(SelectSavePointUiEvent.NavigateToDetailBook(
                            bookId,savePoint.itemPosIndex
                        ))
                    }
                    is SavePointType.Search->{
                        _savePointUiEvent.send(SelectSavePointUiEvent.NavigateToSearch(
                            type.query,type.criteria.keyValue,savePoint.itemPosIndex
                        ))
                    }
                    is SavePointType.List->{
                        _savePointUiEvent.send(SelectSavePointUiEvent.NavigateToList(
                            type.listId,savePoint.itemPosIndex
                        ))
                    }
                }
            }
        }
    }

}


















