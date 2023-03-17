package com.masterplus.mesnevi.features.savepoint.presentation.select_savepoint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.util.ToastHelper
import com.masterplus.mesnevi.core.domain.util.UiEvent
import com.masterplus.mesnevi.core.presentation.components.CustomDropdownMenu
import com.masterplus.mesnevi.core.presentation.components.buttons.PrimaryButton
import com.masterplus.mesnevi.core.presentation.components.navigation.CustomTopAppBar
import com.masterplus.mesnevi.core.presentation.dialog_body.ShowGetTextDialog
import com.masterplus.mesnevi.core.presentation.dialog_body.ShowQuestionDialog
import com.masterplus.mesnevi.features.savepoint.domain.model.SavePoint
import com.masterplus.mesnevi.features.savepoint.presentation.components.SavePointItem
import kotlinx.coroutines.flow.collectLatest

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun SelectSavePointPage(
    title: String,
    typeIds: List<Int>,
    onNavigateBack: ()->Unit,
    onNavigateToDetailBook: (bookId: Int,pos: Int)->Unit,
    onNavigateToList: (listId: Int,pos: Int)->Unit,
    onNavigateToSearch: (query: String, criteriaValue: Int, pos: Int)->Unit,
    viewModel: SelectSavePointViewModel = hiltViewModel()
){

    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(typeIds){
        viewModel.onEvent(SelectSavePointEvent.LoadData(typeIds))
    }

    LaunchedEffect(viewModel.uiEvent){
        viewModel.uiEvent.collectLatest {event->
            when(event){
                is UiEvent.ShowMessage->{
                    ToastHelper.showMessage(event.message,context)
                }
            }
        }
    }

    LaunchedEffect(true){
        viewModel.savePointUiEvent.collectLatest { event->
            when(event){
                is SelectSavePointUiEvent.NavigateToDetailBook -> {
                    onNavigateToDetailBook(event.bookId,event.pos)
                }
                is SelectSavePointUiEvent.NavigateToList -> {
                    onNavigateToList(event.listId,event.pos)
                }
                is SelectSavePointUiEvent.NavigateToSearch -> {
                    onNavigateToSearch(event.query,event.criteriaValue,event.pos)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = stringResource(R.string.save_points),
                onNavigateBack = onNavigateBack
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Column(
            modifier = Modifier.padding(it).fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {

                if(state.showDropdownMenu){
                    item {
                        CustomDropdownMenu(
                            items = state.dropdownItems,
                            currentItem = state.selectedDropdownItem,
                            onItemChange = {viewModel.onEvent(SelectSavePointEvent.SelectDropdownMenuItem(it))}
                        )
                    }
                }

                if(state.savePoints.isEmpty()){
                    item {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillParentMaxSize()
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
                        key = {item->item.id?:0},
                    ){item: SavePoint ->
                        SavePointItem(
                            savePoint = item,
                            onClick = {viewModel.onEvent(SelectSavePointEvent.Select(item))},
                            isSelected = state.selectedSavePoint == item,
                            onTitleEditClick = {
                                viewModel.onEvent(
                                    SelectSavePointEvent.ShowDialog(
                                        true,SelectSavePointDialogEvent.EditTitle(item)
                                    )
                                )
                            },
                            onDeleteClick = {
                                viewModel.onEvent(
                                    SelectSavePointEvent.ShowDialog(
                                        true,SelectSavePointDialogEvent.AskDelete(item)
                                    )
                                )
                            }
                        )
                    }
                }
            }
            PrimaryButton(
                title = stringResource(R.string.load_and_go),
                onClick = {viewModel.onEvent(SelectSavePointEvent.LoadSavePoint)},
                modifier = Modifier.padding(vertical = 3.dp, horizontal = 5.dp)
                    .fillMaxWidth()
            )
        }
    }

    if(state.showDialog){
        ShowDialog(
            state.modalDialog,
            onEvent = {viewModel.onEvent(it)}
        )
    }

}


@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
private fun ShowDialog(
    event: SelectSavePointDialogEvent?,
    onEvent: (SelectSavePointEvent)->Unit
){
    when(event){
        is SelectSavePointDialogEvent.AskDelete->{
            ShowQuestionDialog(
                title = stringResource(R.string.question_delete),
                content = stringResource(R.string.not_revertable),
                onApproved = {onEvent(SelectSavePointEvent.Delete(event.savePoint))},
                onClosed = {onEvent(SelectSavePointEvent.ShowDialog(false))}
            )
        }
        is SelectSavePointDialogEvent.EditTitle ->{
            ShowGetTextDialog(
                title = stringResource(R.string.edit_save_point),
                content = event.savePoint.title,
                onApproved = {onEvent(SelectSavePointEvent.EditTitle(it,event.savePoint))},
                onClosed = {onEvent(SelectSavePointEvent.ShowDialog(false))}
            )
        }
        else -> {}
    }
}