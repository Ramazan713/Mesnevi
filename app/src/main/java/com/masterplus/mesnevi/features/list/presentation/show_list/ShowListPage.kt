package com.masterplus.mesnevi.features.list.presentation.show_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.constants.SavePointType
import com.masterplus.mesnevi.core.domain.util.ToastHelper
import com.masterplus.mesnevi.core.domain.util.UiEvent
import com.masterplus.mesnevi.core.domain.util.UiText
import com.masterplus.mesnevi.core.presentation.components.CustomDropdownBarMenu
import com.masterplus.mesnevi.core.presentation.components.CustomModalBottomSheet
import com.masterplus.mesnevi.core.presentation.components.navigation.CustomTopAppBar
import com.masterplus.mesnevi.core.presentation.dialog_body.SelectMenuItemBottomContent
import com.masterplus.mesnevi.core.presentation.dialog_body.ShowGetTextDialog
import com.masterplus.mesnevi.core.presentation.dialog_body.ShowQuestionDialog
import com.masterplus.mesnevi.core.presentation.extensions.isScrollingUp

import com.masterplus.mesnevi.features.app.presentation.navigation.BottomBarVisibilityViewModel
import com.masterplus.mesnevi.features.list.presentation.components.ListViewItem
import com.masterplus.mesnevi.features.list.presentation.show_list.constants.ShowListBarMenuEnum
import com.masterplus.mesnevi.features.list.presentation.show_list.constants.ShowListBottomMenuEnum
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@ExperimentalMaterial3Api
@Composable
fun ShowListPage(
    onNavigateToArchive: ()->Unit,
    onNavigateToDetailList: (listId: Int)->Unit,
    onNavigateToSelectSavePoint: (String,typeIds: List<Int>)->Unit,
    listViewModel: ShowListViewModel = hiltViewModel()
){
    val state = listViewModel.state
    val context = LocalContext.current

    val lazyListState = rememberLazyListState()
    val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    LaunchedEffect(true){
        listViewModel.uiEvent.collectLatest { event->
            when(event){
                is UiEvent.ShowMessage -> {
                    ToastHelper.showMessage(event.message,context)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = stringResource(R.string.list),
                scrollBehavior = topAppBarScrollBehavior,
                actions = {
                    IconButton(onClick = {
                        onNavigateToArchive()
                    }){
                        Icon(painterResource(R.drawable.ic_baseline_archive_24),contentDescription = null)
                    }
                    CustomDropdownBarMenu(
                        items = ShowListBarMenuEnum.values().toList(),
                        onItemChange = {menuItem->
                            when(menuItem){
                                ShowListBarMenuEnum.showSelectSavePoint -> {
                                    onNavigateToSelectSavePoint(
                                        UiText.Resource(R.string.notebook_save_points).asString(context),
                                        listOf(SavePointType.List.typeId)
                                    )
                                }
                            }
                        },
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                listViewModel.onEvent(ShowListEvent.ShowDialog(true,
                    ShowListDialogEvent.TitleToAddList
                ))
            }){
                Icon(Icons.Default.Add,contentDescription = stringResource(R.string.add_list))
            }
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
    ){paddings->
        LazyColumn(
            modifier = Modifier.padding(paddings)
                .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
            state = lazyListState
        ){
            item {
                if(state.items.isEmpty()){
                    Text(
                        stringResource(R.string.list_empty_text),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 100.dp)
                    )
                }
            }
            items(
                state.items,
                key = {item->item.id?:0}
            ){item->
                ListViewItem(
                    listView = item,
                    onClick = {
                        onNavigateToDetailList(item.id?:0)
                    },
                    onMenuClick = {
                        listViewModel.onEvent(ShowListEvent.ShowModal(true,
                            ShowListModelEvent.ShowSelectBottomMenu(item),
                        ))
                    }
                )
            }
        }
    }

    if(state.showModal){
        ShowModal(
            state.modalEvent,
            onEvent = {listViewModel.onEvent(it)}
        )
    }else if(state.showDialog){
        ShowDialog(
            event = state.dialogEvent,
            onEvent = {listViewModel.onEvent(it)}
        )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowModal(
    event: ShowListModelEvent?,
    onEvent: (ShowListEvent)->Unit
){
    CustomModalBottomSheet(
        onDismissRequest = {onEvent(ShowListEvent.ShowModal(false))}
    ){
        when(event){
            is ShowListModelEvent.ShowSelectBottomMenu -> {
                SelectMenuItemBottomContent(
                    title = stringResource(R.string.n_for_list_item,event.listView.name),
                    items = ShowListBottomMenuEnum.values()
                        .filter {
                            if(!event.listView.isRemovable){
                                return@filter listOf(ShowListBottomMenuEnum.rename,
                                    ShowListBottomMenuEnum.copy).contains(it)
                            }
                            true
                        }
                        .toList(),
                    onClickItem = {selected->
                        onEvent(ShowListEvent.ShowModal(false))
                        when(selected){
                            ShowListBottomMenuEnum.delete->{
                                onEvent(ShowListEvent.ShowDialog(true,
                                    ShowListDialogEvent.AskDelete(event.listView),
                                ))
                            }
                            ShowListBottomMenuEnum.rename -> {
                                onEvent(ShowListEvent.ShowDialog(true,
                                    ShowListDialogEvent.Rename(event.listView),
                                ))
                            }
                            ShowListBottomMenuEnum.archive -> {
                                onEvent(ShowListEvent.ShowDialog(true,
                                    ShowListDialogEvent.AskArchive(event.listView),
                                ))
                            }
                            ShowListBottomMenuEnum.copy -> {
                                onEvent(ShowListEvent.ShowDialog(true,
                                    ShowListDialogEvent.AskCopy(event.listView),
                                ))
                            }
                            null -> {

                            }
                        }
                    }
                )
            }
            null -> {

            }
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
private fun ShowDialog(
    event: ShowListDialogEvent?,
    onEvent: (ShowListEvent)->Unit
){
    when(event){
        is ShowListDialogEvent.AskDelete -> {
            ShowQuestionDialog(
                title = stringResource(R.string.question_delete),
                content = stringResource(R.string.not_revertable),
                onApproved = {onEvent(ShowListEvent.Delete(event.listView))},
                onClosed = {onEvent(ShowListEvent.ShowDialog(false))}
            )
        }
        is ShowListDialogEvent.TitleToAddList -> {
            ShowGetTextDialog(
                title = stringResource(R.string.enter_title),
                onClosed = {onEvent(ShowListEvent.ShowDialog(false))},
                onApproved = {onEvent(ShowListEvent.AddNewList(it))}
            )
        }
        null -> {}
        is ShowListDialogEvent.AskArchive -> {
            ShowQuestionDialog(
                title = stringResource(R.string.question_archive_title),
                content = stringResource(R.string.question_archive_content),
                onApproved = {onEvent(ShowListEvent.Archive(event.listView))},
                onClosed = {onEvent(ShowListEvent.ShowDialog(false))}
            )
        }
        is ShowListDialogEvent.AskCopy -> {
            ShowQuestionDialog(
                title = stringResource(R.string.question_copy),
                onApproved = {onEvent(ShowListEvent.Copy(event.listView))},
                onClosed = {onEvent(ShowListEvent.ShowDialog(false))}
            )
        }
        is ShowListDialogEvent.Rename -> {
            ShowGetTextDialog(
                title = stringResource(R.string.rename),
                content = event.listView.name,
                onClosed = {onEvent(ShowListEvent.ShowDialog(false))},
                onApproved = {onEvent(ShowListEvent.Rename(event.listView,it))}
            )
        }
    }
}













