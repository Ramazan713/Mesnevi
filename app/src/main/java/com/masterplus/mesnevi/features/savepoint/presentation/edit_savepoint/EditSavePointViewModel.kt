package com.masterplus.mesnevi.features.savepoint.presentation.edit_savepoint

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.constants.SavePointType
import com.masterplus.mesnevi.core.domain.util.UiEvent
import com.masterplus.mesnevi.core.domain.util.UiText
import com.masterplus.mesnevi.features.savepoint.domain.use_case.SavePointsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditSavePointViewModel @Inject constructor(
    private val savePointsUseCases: SavePointsUseCases
): ViewModel(){

    var state by mutableStateOf(EditSavePointState())
        private set

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var loadDataJob: Job? = null

    fun onEvent(event: EditSavePointEvent){
        when(event){
            is EditSavePointEvent.Delete -> {
                viewModelScope.launch {
                    savePointsUseCases.deleteSavePoint(event.savePoint)
                    _uiEvent.send(UiEvent.ShowMessage(UiText.Resource(R.string.successfully_deleted)))
                }
            }
            is EditSavePointEvent.EditTitle -> {
                viewModelScope.launch{
                    savePointsUseCases.updateSavePoint(event.savePoint.copy(title = event.title))
                    _uiEvent.send(UiEvent.ShowMessage(UiText.Resource(R.string.successfully_updated)))
                }
            }
            is EditSavePointEvent.LoadData -> {
                loadData(event)
            }
            is EditSavePointEvent.Select -> {
                state = state.copy(selectedSavePoint = event.savePoint)
            }
            is EditSavePointEvent.AddSavePoint -> {
                viewModelScope.launch {
                    SavePointType.fromTypeId(event.typeId,event.saveKey)?.let { savePointType->
                        savePointsUseCases.insertSavePoint(
                            itemPosIndex = event.pos,
                            savePointType = savePointType,
                            title = event.title
                        )
                        _uiEvent.send(UiEvent.ShowMessage(UiText.Resource(R.string.successfully_added)))
                    }
                }
            }
            is EditSavePointEvent.OverrideSavePoint -> {
                viewModelScope.launch {
                    state.selectedSavePoint?.let { savePoint ->
                        savePointsUseCases.updateSavePoint(
                            savePoint.copy(itemPosIndex = event.pos)
                        )
                        _uiEvent.send(UiEvent.ShowMessage(UiText.Resource(R.string.successfully_updated)))
                    }
                }
            }
            is EditSavePointEvent.ShowDialog -> {
                state = state.copy(dialogEvent = event.dialogEvent, showDialog = event.showDialog)
            }
        }
    }


    private fun loadData(event: EditSavePointEvent.LoadData){
        loadDataJob?.cancel()
        loadDataJob = viewModelScope.launch {
            savePointsUseCases.getSavePoints(event.typeId,event.saveKey).collectLatest { items->
                state = state.copy(savePoints = items)
            }
        }
    }

}