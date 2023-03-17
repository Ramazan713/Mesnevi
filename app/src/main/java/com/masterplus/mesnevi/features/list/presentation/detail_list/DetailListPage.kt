package com.masterplus.mesnevi.features.list.presentation.detail_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.constants.SavePointType
import com.masterplus.mesnevi.core.domain.constants.ShareItemEnum
import com.masterplus.mesnevi.core.presentation.components.CustomDropdownBarMenu
import com.masterplus.mesnevi.core.presentation.components.CustomModalBottomSheet
import com.masterplus.mesnevi.core.presentation.components.RotatableLaunchEffect
import com.masterplus.mesnevi.core.presentation.components.ShowContentNotes
import com.masterplus.mesnevi.core.presentation.components.navigation.CustomTopAppBar
import com.masterplus.mesnevi.core.presentation.dialog_body.SelectFontSizeBottomContent
import com.masterplus.mesnevi.core.presentation.dialog_body.SelectMenuItemDialog
import com.masterplus.mesnevi.core.presentation.dialog_body.ShowSelectNumberDialog
import com.masterplus.mesnevi.core.presentation.extensions.copyText
import com.masterplus.mesnevi.core.presentation.extensions.shareLink
import com.masterplus.mesnevi.core.presentation.extensions.shareText
import com.masterplus.mesnevi.core.presentation.extensions.visibleMiddlePosition
import com.masterplus.mesnevi.core.presentation.features.select_list.SelectMenuWithListBottom
import com.masterplus.mesnevi.features.list.presentation.detail_list.constants.ListBarMenuEnum
import com.masterplus.mesnevi.features.list.presentation.detail_list.constants.DetailListBottomMenuEnum
import com.masterplus.mesnevi.features.savepoint.presentation.edit_savepoint.EditSavePointPage
import com.masterplus.mesnevi.features.search.presentation.search_result.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalMaterial3Api
@Composable
fun DetailListPage(
    listId: Int,
    scrollPos: Int,
    onNavigateBack: ()->Unit,
    onNavigateToShowBook: (Int,Int)->Unit,
    listViewModel: DetailListViewModel = hiltViewModel()
){
    val state = listViewModel.state
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val topBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    RotatableLaunchEffect {
        listViewModel.onEvent(DetailListEvent.LoadData(listId,scrollPos))
    }

    RotatableLaunchEffect {
        delay(300)
        lazyListState.animateScrollToItem(scrollPos)
    }


    LaunchedEffect(true){
        listViewModel.listUiEvent.collectLatest { event->
            when(event){
                is DetailListUiEvent.NavigateToShowBook -> {
                    onNavigateToShowBook(event.bookId,event.pos)
                }
                is DetailListUiEvent.ShareText -> {
                    when(event.shareType){
                        ShareItemEnum.shareText -> {
                            event.contentModel.shareText(context,event.bookId)
                        }
                        ShareItemEnum.shareLink -> {
                            event.contentModel.shareLink(context,event.bookId,event.pos)
                        }
                    }
                }
                is DetailListUiEvent.CopyClipboard -> {
                    event.contentModel.copyText(context,event.bookId,clipboardManager)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = state.listName,
                onNavigateBack = onNavigateBack,
                scrollBehavior = topBarScrollBehavior,
                actions = {
                    IconButton(onClick = {
                        listViewModel.onEvent(DetailListEvent.ShowDialog(true,
                            DetailListDialogEvent.ShowSelectNumber))
                    }){
                        Icon(painter = painterResource(R.drawable.baseline_map_24),
                        contentDescription = stringResource(R.string.navigator)
                        )
                    }
                    CustomDropdownBarMenu(
                        items = ListBarMenuEnum.values().toList(),
                        onItemChange = {menuItem->
                            when(menuItem){
                                ListBarMenuEnum.showSelectSavePoint -> {
                                    listViewModel.onEvent(DetailListEvent.ShowDialog(true,
                                        DetailListDialogEvent.EditSavePoint()))
                                }
                                ListBarMenuEnum.selectFontSize -> {
                                    listViewModel.onEvent(DetailListEvent.ShowModal(true,
                                    DetailListModalEvent.SelectFontSize))
                                }
                            }
                        },
                    )
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ){padding->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize()
                .nestedScroll(topBarScrollBehavior.nestedScrollConnection),
            state = lazyListState
        ){


            item {
                if(state.items.isEmpty()){
                    Text(
                        "'${state.listName}' listesi boş görünüyor",
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = 100.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }


            itemsIndexed(
                state.items,
                key = {index,item->item.contentModel.id?:0}
            ){index,item->
                Column {
                    ShowContentNotes(item,
                        pos = index,
                        showPos = true,
                        fontSize = state.fontSize,
                        modifier = Modifier.padding(vertical = 3.dp, horizontal = 1.dp)
                            .fillMaxWidth(),
                        onLongClick = {
                            listViewModel.onEvent(
                                DetailListEvent.ShowModal(true,
                                    DetailListModalEvent.ShowSelectBottomMenu(item.contentModel,index)
                                )
                            )
                        }
                    )
                }
            }
        }


        if(state.showModal){
            ShowModal(
                state = listViewModel.state,
                onEvent = {listViewModel.onEvent(it)}
            )
        }
        if(state.showDialog){
            ShowDialog(
                event = state.dialogEvent,
                onEvent = {listViewModel.onEvent(it)},
                onScrollTo = {scope.launch { lazyListState.animateScrollToItem(it) }},
                listId = listId, listName = state.listName,
                lazyListState = lazyListState
            )
        }


    }
}


@ExperimentalFoundationApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowModal(
    state: DetailListState,
    onEvent: (DetailListEvent)->Unit
){

    fun close(){
        onEvent(DetailListEvent.ShowModal(false))
    }

    CustomModalBottomSheet(
        onDismissRequest = {close()},
        skipHalfExpanded = true
    ) {
        when(val event = state.modalEvent){
            is DetailListModalEvent.ShowSelectBottomMenu->{
                SelectMenuWithListBottom(
                    items = DetailListBottomMenuEnum.values().toList(),
                    title = stringResource(R.string.n_for_number_item,event.contentModel.itemNumber),
                    contentModel = event.contentModel,
                    onDismiss = {close()},
                    onClickItem = {selected ->
                        selected?.let { menuEnum->
                            when(menuEnum){
                                DetailListBottomMenuEnum.goNotebook->{
                                    onEvent(DetailListEvent.ShowBookNavigateClick(event.contentModel))
                                }
                                DetailListBottomMenuEnum.editBook->{
                                    close()
                                    onEvent(DetailListEvent.ShowDialog(true,
                                        DetailListDialogEvent.EditSavePoint(event.pos))
                                    )
                                }
                                DetailListBottomMenuEnum.shareText->{
                                    onEvent(DetailListEvent.ShowDialog(true,
                                        DetailListDialogEvent.Share(event.contentModel)
                                    ))
                                }
                                DetailListBottomMenuEnum.copyText->{
                                    onEvent(DetailListEvent.CopyClipboard(event.contentModel))
                                }
                            }
                        }
                    },
                )
            }
            is DetailListModalEvent.SelectFontSize->{
                SelectFontSizeBottomContent(
                    selected = state.fontSize,
                    onClickItem = {onEvent(DetailListEvent.SetFontSize(it))},
                    onClose = {close()}
                )
            }
            else -> {
                Text("")
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ShowDialog(
    event: DetailListDialogEvent?,
    onEvent: (DetailListEvent)->Unit,
    onScrollTo: (Int)->Unit,
    lazyListState: LazyListState,
    listId: Int,
    listName: String
){
    val middlePos = lazyListState.visibleMiddlePosition()
    val maxPos by remember {
        derivedStateOf { lazyListState.layoutInfo.totalItemsCount }
    }

    fun close(){
        onEvent(DetailListEvent.ShowDialog(false))
    }

    when(event){
        is DetailListDialogEvent.EditSavePoint -> {
            EditSavePointPage(
                SavePointType.List.typeId,
                SavePointType.List(listId).toSaveKey(),
                event.pos?: middlePos,
                listName,
                onClosed = { close() },
                onNavigateLoad = {
                    onScrollTo(it.itemPosIndex)
                }
            )
        }
        is DetailListDialogEvent.ShowSelectNumber->{
            ShowSelectNumberDialog(
                minValueParam = 0,
                maxValueParam = maxPos - 1,
                onApprove = {onScrollTo(it)},
                currentValue = middlePos,
                onClose = { close() }
            )
        }
        null -> {

        }
        is DetailListDialogEvent.Share -> {
            SelectMenuItemDialog(
                items = ShareItemEnum.values().toList(),
                onClickItem = {
                    onEvent(DetailListEvent.Share(event.contentModel,it))
                },
                onClosed = { close() },
                title = stringResource(R.string.share_choices)
            )
        }
    }
}













