package com.masterplus.mesnevi.features.search.presentation.search_result

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
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
import com.masterplus.mesnevi.core.presentation.components.ShowContentNotesSearchable
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.model.ContentModel
import com.masterplus.mesnevi.core.domain.constants.SavePointType
import com.masterplus.mesnevi.core.domain.constants.ShareItemEnum
import com.masterplus.mesnevi.core.presentation.components.CustomDropdownBarMenu
import com.masterplus.mesnevi.core.presentation.components.CustomModalBottomSheet
import com.masterplus.mesnevi.core.presentation.components.RotatableLaunchEffect
import com.masterplus.mesnevi.core.presentation.components.navigation.CustomTopAppBar
import com.masterplus.mesnevi.core.presentation.dialog_body.SelectFontSizeBottomContent
import com.masterplus.mesnevi.core.presentation.dialog_body.SelectMenuItemBottomContent
import com.masterplus.mesnevi.core.presentation.dialog_body.SelectMenuItemDialog
import com.masterplus.mesnevi.core.presentation.dialog_body.ShowSelectNumberDialog
import com.masterplus.mesnevi.core.presentation.extensions.copyText
import com.masterplus.mesnevi.core.presentation.extensions.shareLink
import com.masterplus.mesnevi.core.presentation.extensions.shareText
import com.masterplus.mesnevi.core.presentation.extensions.visibleMiddlePosition
import com.masterplus.mesnevi.core.presentation.features.select_list.SelectMenuWithListBottom
import com.masterplus.mesnevi.features.savepoint.presentation.edit_savepoint.EditSavePointPage
import com.masterplus.mesnevi.features.search.domain.model.SearchCriteria
import com.masterplus.mesnevi.features.search.presentation.search_result.constants.SearchBarMenuEnum
import com.masterplus.mesnevi.features.search.presentation.search_result.constants.SearchBottomMenuEnum
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun SearchResultPage(
    query: String,
    criteriaValue: Int,
    scrollPos: Int,
    onNavigateBack: ()->Unit,
    onNavigateToShowBook: (Int,Int)->Unit,
    searchViewModel: SearchResultViewModel = hiltViewModel()
){
    val lazyListState = rememberLazyListState()
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    val state = searchViewModel.state
    val scope = rememberCoroutineScope()
    val topBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    RotatableLaunchEffect {
       searchViewModel.onEvent(SearchResultEvent.Search(query, criteriaValue,scrollPos))
    }

    LaunchedEffect(true){
        searchViewModel.searchUiEvent.collectLatest { event->
            when(event){
                is SearchResultUiEvent.NavigateToShowBook -> {
                    onNavigateToShowBook(event.bookId,event.pos)
                }
                is SearchResultUiEvent.ScrollTo -> {
                    lazyListState.animateScrollToItem(scrollPos)
                }
                is SearchResultUiEvent.ShareText -> {
                    when(event.shareType){
                        ShareItemEnum.shareText -> {
                            event.contentModel.shareText(context,event.bookId)
                        }
                        ShareItemEnum.shareLink -> {
                            event.contentModel.shareLink(context,event.bookId,event.pos)
                        }
                    }
                }
                is SearchResultUiEvent.CopyClipboard -> {
                    event.contentModel.copyText(context,event.bookId,clipboardManager)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = query,
                onNavigateBack = onNavigateBack,
                scrollBehavior = topBarScrollBehavior,
                actions = ShowTopBarActions(
                    state = searchViewModel.state,
                    onEvent = {searchViewModel.onEvent(it)},
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ){padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize()
                .nestedScroll(topBarScrollBehavior.nestedScrollConnection),
            state = lazyListState
        ){


            item {
                if(state.items.isEmpty()){
                    Text(
                        stringResource(R.string.n_no_search_result,query),
                        modifier = Modifier.padding(vertical = 100.dp).fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }else{
                    Text(
                        stringResource(R.string.n_search_result_found,state.items.size),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = 3.dp, vertical = 1.dp)
                    )
                }
            }

            itemsIndexed(
                state.items,
                key = {index,item->item.contentModel.id?:0}
            ){index,item->
                Column {
                    ShowContentNotesSearchable(item,
                        pos = index,
                        showPos = true,
                        modifier = Modifier.padding(vertical = 3.dp, horizontal = 1.dp)
                            .fillMaxWidth(),
                        fontSize = state.fontSize,
                        onLongClick = {
                            searchViewModel.onEvent(
                                SearchResultEvent.ShowModal(true,
                                    SearchResultModalEvent.ShowSelectBottomMenu(item.contentModel,index)
                                )
                            )
                        }
                    )
                }
            }
        }
    }

    if(state.showModal){
        ShowModal(
            state = searchViewModel.state,
            onEvent = {searchViewModel.onEvent(it)}
        )
    }
    if(state.showDialog){
        ShowDialog(
            event = state.dialogEvent,
            onEvent = {searchViewModel.onEvent(it)},
            onScrollTo = {scope.launch { lazyListState.animateScrollToItem(it) }},
            query = state.query, criteria = state.criteria,
            lazyListState = lazyListState
        )
    }

}


@Composable
private fun ShowTopBarActions(
    state: SearchResultState,
    onEvent: (SearchResultEvent) -> Unit,
): @Composable (RowScope.() -> Unit){

    return {
        IconButton(
            onClick = {
                onEvent(
                    SearchResultEvent.ShowDialog(true, SearchResultDialogEvent.ShowSelectNumber))
            },
        ){
            Icon(painter = painterResource(R.drawable.baseline_map_24), contentDescription = stringResource(R.string.navigator))
        }
        CustomDropdownBarMenu(
            items = SearchBarMenuEnum.values().filter {
                if(it == SearchBarMenuEnum.clearSearchQuery){
                    return@filter state.showClearIcon
                }
                true
            }.toList(),
            onItemChange = {menuItem->
                when(menuItem){
                    SearchBarMenuEnum.editBookBottom -> {
                        onEvent(
                            SearchResultEvent.ShowDialog(true,
                                SearchResultDialogEvent.EditSavePoint(),
                            ))
                    }
                    SearchBarMenuEnum.clearSearchQuery -> {
                        onEvent(SearchResultEvent.ClearSearch)
                    }
                    SearchBarMenuEnum.selectFontSize -> {
                        onEvent(SearchResultEvent.ShowModal(true,
                        SearchResultModalEvent.SelectFontSize))
                    }
                }
            },
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun ShowModal(
    state: SearchResultState,
    onEvent: (SearchResultEvent)->Unit
){

    fun close(){
        onEvent(SearchResultEvent.ShowModal(false))
    }

    CustomModalBottomSheet(
        onDismissRequest = {close()},
        skipHalfExpanded = true
    ) {
        when(val event = state.modalEvent){
            is SearchResultModalEvent.ShowSelectBottomMenu->{
                SelectMenuWithListBottom(
                    onDismiss = {close()},
                    items = SearchBottomMenuEnum.values().toList(),
                    contentModel = event.contentModel,
                    title = stringResource(R.string.n_for_number_item,event.contentModel.itemNumber),
                    onClickItem = {menuEnum->
                        when(menuEnum){
                            SearchBottomMenuEnum.goNotebook->{
                                onEvent(SearchResultEvent.ShowBookNavigateClick(event.contentModel))
                            }
                            SearchBottomMenuEnum.editBook->{
                                close()
                                onEvent(SearchResultEvent.ShowDialog(true,
                                    SearchResultDialogEvent.EditSavePoint(event.pos))
                                )
                            }
                            SearchBottomMenuEnum.shareText -> {
                                onEvent(SearchResultEvent.ShowDialog(true,
                                    SearchResultDialogEvent.Share(event.contentModel)))
                            }
                            SearchBottomMenuEnum.copyText->{
                                onEvent(SearchResultEvent.CopyClipboard(event.contentModel))
                            }
                        }
                    }
                )
            }
            is SearchResultModalEvent.SelectFontSize->{
                SelectFontSizeBottomContent(
                    selected = state.fontSize,
                    onClickItem = {onEvent(SearchResultEvent.SetFontSize(it))},
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
    event: SearchResultDialogEvent?,
    onEvent: (SearchResultEvent)->Unit,
    onScrollTo: (Int)->Unit,
    lazyListState: LazyListState,
    query: String,criteria: SearchCriteria
){
    val visibleMiddlePos = lazyListState.visibleMiddlePosition()
    val maxPos by remember {
        derivedStateOf { lazyListState.layoutInfo.totalItemsCount }
    }

    fun close(){
        onEvent(SearchResultEvent.ShowDialog(false))
    }

    when(event){
        is SearchResultDialogEvent.EditSavePoint -> {
            EditSavePointPage(
                SavePointType.Search.typeId,
                SavePointType.Search(query, criteria).toSaveKey(),
                event.pos?:visibleMiddlePos,
                query,
                onClosed = { close() },
                onNavigateLoad = {
                    onScrollTo(it.itemPosIndex)
                }
            )
        }
        is SearchResultDialogEvent.ShowSelectNumber->{
            ShowSelectNumberDialog(
                minValueParam = 0,
                maxValueParam = maxPos - 1,
                onApprove = {onScrollTo(it)},
                currentValue = visibleMiddlePos,
                onClose = { close() }
            )
        }
        null -> {}
        is SearchResultDialogEvent.Share -> {
            SelectMenuItemDialog(
                items = ShareItemEnum.values().toList(),
                onClickItem = {
                    onEvent(SearchResultEvent.Share(event.contentModel,it))
                },
                onClosed = { close() },
                title = stringResource(R.string.share_choices)
            )
        }
    }
}