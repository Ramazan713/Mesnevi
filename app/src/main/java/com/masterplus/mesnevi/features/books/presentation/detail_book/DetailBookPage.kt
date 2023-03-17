
package com.masterplus.mesnevi.features.books.presentation.detail_book
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.constants.*

import com.masterplus.mesnevi.core.presentation.components.navigation.CustomTopAppBar
import com.masterplus.mesnevi.core.presentation.dialog_body.ShowSelectNumberDialog
import com.masterplus.mesnevi.core.presentation.components.*
import com.masterplus.mesnevi.core.presentation.dialog_body.SelectFontSizeBottomContent
import com.masterplus.mesnevi.core.presentation.dialog_body.SelectMenuItemDialog
import com.masterplus.mesnevi.core.presentation.extensions.copyText
import com.masterplus.mesnevi.core.presentation.extensions.shareLink
import com.masterplus.mesnevi.core.presentation.extensions.shareText
import com.masterplus.mesnevi.core.presentation.extensions.visibleMiddlePosition
import com.masterplus.mesnevi.features.books.domain.model.BookInfoEnum
import com.masterplus.mesnevi.features.savepoint.presentation.edit_savepoint.EditSavePointPage
import com.masterplus.mesnevi.features.books.presentation.detail_book.components.ShowBookDescription
import com.masterplus.mesnevi.features.books.presentation.detail_book.components.ShowContentTitle
import com.masterplus.mesnevi.features.books.presentation.detail_book.constants.DetailBookBottomMenuEnum
import com.masterplus.mesnevi.core.presentation.features.select_list.SelectMenuWithListBottom
import com.masterplus.mesnevi.features.books.presentation.detail_book.constants.DetailBookBarMenuItem
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@Composable
fun DetailBookPage(
    bookId: Int,
    scrollPos: Int = 0,
    onNavigatePop: ()->Unit,
    bookViewModel: DetailBookViewModel = hiltViewModel()
){

    val state = bookViewModel.state
    val lazyColumnState = rememberLazyListState()
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scrollTopBar = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    RotatableLaunchEffect {
        bookViewModel.onEvent(DetailBookEvent.Init(bookId,scrollPos))
    }

    LaunchedEffect(true){
        bookViewModel.bookUiEvent.collectLatest { event->
            when(event){
                is DetailBookUiEvent.ScrollTo -> {
                    lazyColumnState.animateScrollToItem(scrollPos)
                }
                is DetailBookUiEvent.ShareText -> {
                    when(event.shareType){
                        ShareItemEnum.shareText -> {
                            event.contentModel.shareText(context,event.bookId)
                        }
                        ShareItemEnum.shareLink -> {
                            event.contentModel.shareLink(context,event.bookId,event.pos)
                        }
                    }

                }
                is DetailBookUiEvent.CopyClipboard -> {
                    event.contentModel.copyText(context,event.bookId,clipboardManager)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = state.bookInfoEnum.title.asString(),
                onNavigateBack = onNavigatePop,
                scrollBehavior = scrollTopBar,
                actions = ShowTopBarActions(
                    onEvent = {bookViewModel.onEvent(it)},
                ),
            )
        },
        modifier = Modifier.nestedScroll(scrollTopBar.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
    ) { paddings->
        Box(
            modifier = Modifier
                .padding(paddings)
        ){
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                state = lazyColumnState
            ){
                item {
                    if(state.book!=null){
                        ShowBookDescription(state.book,
                            fontSize = state.fontSize,
                            modifier = Modifier.padding(vertical = 3.dp, horizontal = 1.dp).fillMaxWidth(),
                        )
                    }
                }
                itemsIndexed(
                    state.contents,
                    key = {i,item->item.contentNote.contentModel.id?:i}
                ){index,item ->
                    val title = item.titleNotes
                    val contents = item.contentNote
                    Column {
                        if(title!=null&&title.title.content!=""){
                            ShowContentTitle(title,
                                fontSize = state.fontSize,
                                modifier = Modifier.padding(vertical = 7.dp, horizontal = 1.dp)
                                    .fillMaxWidth()
                            )
                        }
                        ShowContentNotes(contents,
                            modifier = Modifier.padding( horizontal = 1.dp)
                                .fillMaxWidth(),
                            useBorder = false,
                            fontSize = state.fontSize,
                            onLongClick = {
                                bookViewModel.onEvent(DetailBookEvent.ShowModal(
                                    true,
                                    DetailBookModalEvent.ShowSelectBottomMenu(contents.contentModel,index)
                                ))
                            }
                        )
                    }
                }
            }
        }
    }

    if(state.showModal){
       ShowModal(
           state = state,
           onEvent = {bookViewModel.onEvent(it)}
       )
    }
    if(state.showDialog){
        ShowDialog(
            state.dialogEvent,
            onEvent = {bookViewModel.onEvent(it)},
            bookInfo = state.bookInfoEnum,
            lazyListState = lazyColumnState,
            onScrollTo = { scope.launch { lazyColumnState.animateScrollToItem(it) }}
        )
    }
}



@Composable
private fun ShowTopBarActions(
    onEvent: (DetailBookEvent) -> Unit,
): @Composable (RowScope.() -> Unit){

    return {
        IconButton(
            onClick = {
                onEvent(DetailBookEvent.ShowDialog(true,
                    DetailBookDialogEvent.ShowSelectNumber))
            },
        ){
            Icon(painter = painterResource(R.drawable.baseline_map_24),
                contentDescription = stringResource(R.string.navigator)
            )
        }
        CustomDropdownBarMenu(
            items = DetailBookBarMenuItem.values().toList(),
            onItemChange = {menuItem->
                when(menuItem){
                    DetailBookBarMenuItem.showSelectSavePoint -> {
                        onEvent(DetailBookEvent.ShowDialog(true,
                            DetailBookDialogEvent.EditSavePoint(),
                        ))
                    }
                    DetailBookBarMenuItem.selectFontSize -> {
                        onEvent(DetailBookEvent.ShowModal(true,
                            DetailBookModalEvent.SelectFontSize))
                    }
                }
            },
        )
    }
}








@ExperimentalComposeUiApi
@Composable
private fun ShowDialog(
    event: DetailBookDialogEvent?,
    onEvent: (DetailBookEvent)->Unit,
    lazyListState: LazyListState,
    bookInfo: BookInfoEnum,
    onScrollTo: (Int)->Unit
){
    val visibleMiddlePos = lazyListState.visibleMiddlePosition()
    val maxPos by remember {
        derivedStateOf { lazyListState.layoutInfo.totalItemsCount }
    }

    fun close(){
        onEvent(DetailBookEvent.ShowDialog(false))
    }

    when(event){
        is DetailBookDialogEvent.EditSavePoint -> {
            EditSavePointPage(
                SavePointType.Book.typeId,
                SavePointType.Book(bookInfo.bookId).toSaveKey(),
                event.pos?:visibleMiddlePos,
                bookInfo.title.asString(),
                onClosed = { close() },
                onNavigateLoad = {
                    onScrollTo(it.itemPosIndex)
                }
            )
        }
        is DetailBookDialogEvent.ShowSelectNumber->{
            ShowSelectNumberDialog(
                minValueParam = 0,
                maxValueParam = maxPos - 1,
                onApprove = {onScrollTo(it)},
                currentValue = visibleMiddlePos,
                onClose = { close() }
            )
        }
        is DetailBookDialogEvent.Share -> {
            SelectMenuItemDialog(
                items = ShareItemEnum.values().toList(),
                onClickItem = {
                    onEvent(DetailBookEvent.Share(event.contentModel,it,event.pos))
                },
                onClosed = { close() },
                title = stringResource(R.string.share_choices)
            )
        }
        null -> {}
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
private fun ShowModal(
    state: DetailBookState,
    onEvent: (DetailBookEvent)->Unit
){

    fun close(){
        onEvent(DetailBookEvent.ShowModal(false))
    }

    CustomModalBottomSheet(
        onDismissRequest = { close() },
        skipHalfExpanded = true
    ){
        when(val event = state.modalEvent){
            is DetailBookModalEvent.ShowSelectBottomMenu -> {
                SelectMenuWithListBottom(
                    items = DetailBookBottomMenuEnum.values().toList(),
                    onClickItem = {selected ->
                        selected?.let {menuItem->
                            when(menuItem){
                                DetailBookBottomMenuEnum.editBookBottom->{
                                    close()
                                    onEvent(DetailBookEvent.ShowDialog(
                                        true,
                                        DetailBookDialogEvent.EditSavePoint(event.pos)
                                    ))
                                }
                                DetailBookBottomMenuEnum.shareText->{
                                    onEvent(DetailBookEvent.ShowDialog(true,
                                        DetailBookDialogEvent.Share(event.contentModel,event.pos)))
                                }
                                DetailBookBottomMenuEnum.copyText->{
                                    onEvent(DetailBookEvent.CopyClipboard(event.contentModel))
                                }
                            }
                        }
                    },
                    contentModel = event.contentModel,
                    onDismiss = {close()},
                    title = stringResource(R.string.n_for_number_item,event.contentModel.itemNumber)
                )
            }
            is DetailBookModalEvent.SelectFontSize->{
                SelectFontSizeBottomContent(
                    selected = state.fontSize,
                    onClickItem = {onEvent(DetailBookEvent.SetFontSize(it))},
                    onClose = { close() }
                )
            }
            else -> {}
        }
    }

}















