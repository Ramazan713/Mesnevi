@file:OptIn(ExperimentalFoundationApi::class)

package com.masterplus.mesnevi.features.savepoint.presentation.edit_savepoint

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.util.ToastHelper
import com.masterplus.mesnevi.core.domain.util.UiEvent
import com.masterplus.mesnevi.core.presentation.components.buttons.PrimaryButton
import com.masterplus.mesnevi.core.presentation.dialog_body.CustomDialog
import com.masterplus.mesnevi.core.presentation.dialog_body.ShowGetTextDialog
import com.masterplus.mesnevi.core.presentation.dialog_body.ShowQuestionDialog
import com.masterplus.mesnevi.features.savepoint.domain.model.SavePoint
import com.masterplus.mesnevi.features.savepoint.presentation.components.SavePointItem
import kotlinx.coroutines.flow.collectLatest

@ExperimentalComposeUiApi
@Composable
fun EditSavePointPage(
    typeId: Int,
    saveKey: String,
    pos: Int,
    shortTitle: String,
    onClosed: ()->Unit,
    onNavigateLoad: (SavePoint)->Unit,
    editViewModel: EditSavePointViewModel = hiltViewModel()
){
    val state = editViewModel.state
    val context = LocalContext.current

    LaunchedEffect(typeId,saveKey){
        editViewModel.onEvent(EditSavePointEvent.LoadData(typeId, saveKey))
    }

    LaunchedEffect(editViewModel.uiEvent){
        editViewModel.uiEvent.collectLatest {event->
            when(event){
                is UiEvent.ShowMessage->{
                    ToastHelper.showMessage(event.message,context)
                }
            }
        }
    }

    CustomDialog(
        onClosed = onClosed,
        modifier = Modifier.heightIn(300.dp)
    ){
        Column(
            modifier = Modifier
                .padding(horizontal = 1.dp, vertical = 3.dp)
        ){
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ){
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ){
                        IconButton(
                            onClick = {onClosed()},
                        ){
                            Icon(Icons.Default.Close,contentDescription = null)
                        }
                    }
                }
                item {
                    Text(
                        stringResource(R.string.save_points),
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = 7.dp, horizontal = 5.dp)
                    )
                }
                item {
                    PrimaryButton(
                        title = stringResource(R.string.add_new_savepoint),
                        onClick = {
                            editViewModel.onEvent(EditSavePointEvent.ShowDialog(
                                showDialog = true,
                                EditSavePointDialogEvent.AddSavePointTitle(SavePoint.getTitle(shortTitle))
                            ))
                        },
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 7.dp)
                            .fillMaxWidth()
                    )
                }
                if(state.savePoints.isEmpty()){
                    item {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(vertical = 19.dp)
                        ) {
                            Text(
                                stringResource(R.string.empty_savepoint),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }else{
                    items(
                        state.savePoints,
                        key = {item->item.id?:0}
                    ){item->
                        SavePointItem(
                            savePoint = item,
                            isSelected = item == state.selectedSavePoint,
                            onClick = {
                                editViewModel.onEvent(EditSavePointEvent.Select(item))
                            },
                            onDeleteClick = {
                                editViewModel.onEvent(EditSavePointEvent.ShowDialog(
                                    showDialog = true,
                                    EditSavePointDialogEvent.AskDelete(item)
                                ))
                            },
                            onTitleEditClick = {
                                editViewModel.onEvent(EditSavePointEvent.ShowDialog(
                                    showDialog = true,
                                    EditSavePointDialogEvent.EditTitle(item)
                                ))
                            }
                        )
                    }
                }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 7.dp),
                        horizontalArrangement = Arrangement.spacedBy(7.dp)
                    ){
                        PrimaryButton(
                            title = stringResource(R.string.load),
                            onClick = {
                                state.selectedSavePoint?.let { savePoint ->
                                    onNavigateLoad(savePoint)
                                    onClosed()
                                }
                            },
                            modifier = Modifier.weight(1f)
                        )
                        PrimaryButton(
                            title = stringResource(R.string.override),
                            onClick = {editViewModel.onEvent(EditSavePointEvent.OverrideSavePoint(pos))},
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }



        }

        if(state.showDialog){
            ShowDialog(
                state.dialogEvent,
                onEvent = {editViewModel.onEvent(it)},
                pos, saveKey, typeId
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
private fun ShowDialog(
    event: EditSavePointDialogEvent?,
    onEvent: (EditSavePointEvent)->Unit,
    pos: Int, saveKey: String,typeId: Int
){
    when(event){
        is EditSavePointDialogEvent.AskDelete -> {
            ShowQuestionDialog(
                title = stringResource(R.string.question_delete),
                content = stringResource(R.string.not_revertable),
                onApproved = {onEvent(EditSavePointEvent.Delete(event.savePoint))},
                onClosed = {onEvent(EditSavePointEvent.ShowDialog(false))}
            )
        }
        is EditSavePointDialogEvent.EditTitle -> {
            ShowGetTextDialog(
                title = stringResource(R.string.rename),
                content = event.savePoint.title,
                onApproved = {onEvent(EditSavePointEvent.EditTitle(it,event.savePoint))},
                onClosed = {onEvent(EditSavePointEvent.ShowDialog(false))}
            )
        }
        is EditSavePointDialogEvent.AddSavePointTitle -> {
            ShowGetTextDialog(
                title = stringResource(R.string.enter_title),
                content = event.title,
                onApproved = {
                    onEvent(EditSavePointEvent
                        .AddSavePoint(pos,it,typeId, saveKey)) },
                onClosed = {onEvent(EditSavePointEvent.ShowDialog(false))}
            )
        }
        else -> {}
    }
}





