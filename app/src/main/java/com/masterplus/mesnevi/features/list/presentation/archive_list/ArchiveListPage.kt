package com.masterplus.mesnevi.features.list.presentation.archive_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.util.ToastHelper
import com.masterplus.mesnevi.core.domain.util.UiEvent
import com.masterplus.mesnevi.core.presentation.components.CustomModalBottomSheet
import com.masterplus.mesnevi.core.presentation.components.navigation.CustomTopAppBar
import com.masterplus.mesnevi.core.presentation.dialog_body.SelectMenuItemBottomContent
import com.masterplus.mesnevi.core.presentation.dialog_body.ShowGetTextDialog
import com.masterplus.mesnevi.core.presentation.dialog_body.ShowQuestionDialog
import com.masterplus.mesnevi.features.list.presentation.archive_list.constants.ArchiveListBottomMenuEnum
import com.masterplus.mesnevi.features.list.presentation.components.ListViewItem
import com.masterplus.mesnevi.features.list.presentation.show_list.*
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
fun ArchiveListPage(
    onNavigateBack: ()->Unit,
    onNavigateToDetailList: (listId: Int)->Unit,
    archiveViewModel: ArchiveListViewModel = hiltViewModel()
){
    val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val state = archiveViewModel.state
    val context = LocalContext.current

    LaunchedEffect(true){
        archiveViewModel.uiEvent.collectLatest { event->
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
                title = stringResource(R.string.archive_n),
                onNavigateBack = onNavigateBack,
                scrollBehavior = topAppBarScrollBehavior
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ){paddings->
        LazyColumn(
            modifier = Modifier.padding(paddings)
                .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
        ) {
            item {
                if(state.items.isEmpty()){
                    Text(
                        stringResource(R.string.archive_empty_text),
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
                        archiveViewModel.onEvent(
                            ArchiveListEvent.ShowModal(true,
                                ArchiveListModalEvent.ShowSelectBottomMenu(item),
                            ))
                    }
                )
            }
        }
    }

    if(state.showModal){
        ShowModal(
            state.modalEvent,
            onEvent = {archiveViewModel.onEvent(it)}
        )
    }else if(state.showDialog){
        ShowDialog(
            event = state.dialogEvent,
            onEvent = {archiveViewModel.onEvent(it)}
        )
    }

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowModal(
    event: ArchiveListModalEvent?,
    onEvent: (ArchiveListEvent)->Unit
){
    CustomModalBottomSheet(
        onDismissRequest = {onEvent(ArchiveListEvent.ShowModal(false))}
    ){
        when(event){
            is ArchiveListModalEvent.ShowSelectBottomMenu -> {
                SelectMenuItemBottomContent(
                    title = stringResource(R.string.n_for_list_item,event.listView.name),
                    items = ArchiveListBottomMenuEnum.values().toList(),
                    onClickItem = {selected->
                        onEvent(ArchiveListEvent.ShowModal(false))
                        when(selected){
                            ArchiveListBottomMenuEnum.delete->{
                                onEvent(ArchiveListEvent.ShowDialog(true,
                                    ArchiveListDialogEvent.AskDelete(event.listView),
                                ))
                            }
                            ArchiveListBottomMenuEnum.rename -> {
                                onEvent(ArchiveListEvent.ShowDialog(true,
                                    ArchiveListDialogEvent.Rename(event.listView),
                                ))
                            }
                            ArchiveListBottomMenuEnum.unArchive -> {
                                onEvent(ArchiveListEvent.ShowDialog(true,
                                    ArchiveListDialogEvent.AskUnArchive(event.listView),
                                ))
                            }
                            null -> {}
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
    event: ArchiveListDialogEvent?,
    onEvent: (ArchiveListEvent)->Unit
){
    when(event){
        is ArchiveListDialogEvent.AskDelete -> {
            ShowQuestionDialog(
                title = stringResource(R.string.question_delete),
                content = stringResource(R.string.not_revertable),
                onApproved = {onEvent(ArchiveListEvent.Delete(event.listView))},
                onClosed = {onEvent(ArchiveListEvent.ShowDialog(false))}
            )
        }
        null -> {}
        is ArchiveListDialogEvent.AskUnArchive -> {
            ShowQuestionDialog(
                title = stringResource(R.string.question_unarchive),
                onApproved = {onEvent(ArchiveListEvent.UnArchive(event.listView))},
                onClosed = {onEvent(ArchiveListEvent.ShowDialog(false))}
            )
        }
        is ArchiveListDialogEvent.Rename -> {
            ShowGetTextDialog(
                title = stringResource(R.string.rename),
                content = event.listView.name,
                onClosed = {onEvent(ArchiveListEvent.ShowDialog(false))},
                onApproved = {onEvent(ArchiveListEvent.Rename(event.listView,it))}
            )
        }
    }
}
