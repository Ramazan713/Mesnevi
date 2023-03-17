package com.masterplus.mesnevi.features.books.presentation.show_books

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.util.UiText
import com.masterplus.mesnevi.features.app.presentation.navigation.BottomBarVisibilityViewModel
import com.masterplus.mesnevi.core.domain.constants.SavePointType
import com.masterplus.mesnevi.core.presentation.extensions.hasScrolledToTop
import com.masterplus.mesnevi.core.presentation.extensions.isScrollingUp
import com.masterplus.mesnevi.features.books.domain.model.Book
import com.masterplus.mesnevi.features.books.presentation.show_books.components.ShowBookItem
import com.masterplus.mesnevi.features.books.domain.model.History
import com.masterplus.mesnevi.features.search.domain.model.SearchCriteria
import com.masterplus.mesnevi.features.search.presentation.components.HistoryItem
import kotlinx.coroutines.flow.collectLatest


@ExperimentalMaterial3Api
@Composable
fun ShowBookPage(
    onNavigateToDetailBook: (Book)->Unit,
    onNavigateToSelectSavePoint: (String,typeIds: List<Int>)->Unit,
    onNavigateToSearchResult: (String, SearchCriteria)->Unit,
    showBookViewModel: ShowBookViewModel = hiltViewModel()
){
    val state = showBookViewModel.state
    val lazyListState = rememberLazyListState()
    val hasScrolledTop = lazyListState.hasScrolledToTop()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(state.searchBarActive){
        if(!state.searchBarActive) focusManager.clearFocus()
    }

    LaunchedEffect(true){
        showBookViewModel.bookUiEvent.collectLatest { event->
            when(event){
                is ShowBookUiEvent.NavigateToResult->{
                    onNavigateToSearchResult(event.query,event.criteria)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            ShowSearchTopBar(
                query = state.query,
                hasFocus = state.searchBarActive,
                histories = state.histories,
                onEvent = {showBookViewModel.onEvent(it)},
                isVisible = hasScrolledTop
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
    ) {paddings->
        LazyColumn(
            modifier = Modifier.padding(paddings)
                .padding(horizontal = 7.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            state = lazyListState,
        ){

            item {
                Text(
                    stringResource(R.string.mesnevi_notebooks),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                    textAlign = TextAlign.Center
                )
            }

            if(state.isLoading){
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator()
                    }
                }
            }else{
                items(state.books){book->
                    ShowBookItem(
                        title = book.name,
                        onClick = {
                            onNavigateToDetailBook(book)
                        },
                        modifier = Modifier.padding(vertical = 7.dp)
                    )
                }
                item {
                    ShowBookItem(
                        title = UiText.Resource(R.string.save_points).asString(),
                        onClick = {
                            onNavigateToSelectSavePoint(
                                UiText.Resource(R.string.notebook_save_points).asString(context),
                                listOf(SavePointType.Book.typeId, SavePointType.Search.typeId)
                            )
                        },
                        modifier = Modifier.padding(vertical = 13.dp)
                    )
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun ShowSearchTopBar(
    query: String,
    hasFocus: Boolean,
    histories: List<History>,
    onEvent: (ShowBookEvent)->Unit,
    isVisible: Boolean
){
    val backgroundColor = MaterialTheme.colorScheme.primary
    val onBackgroundColor = MaterialTheme.colorScheme.onPrimary

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ){
        Box(
            Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .padding(bottom = 7.dp),
        ) {
            SearchBar(
                modifier = Modifier.align(Alignment.TopCenter),
                query = query,
                colors = SearchBarDefaults.colors(
                    containerColor = onBackgroundColor,
                    inputFieldColors = TextFieldDefaults.textFieldColors(
                        focusedTrailingIconColor = onBackgroundColor
                    )
                ),
                onQueryChange = { onEvent(ShowBookEvent.ChangeQuery(it)) },
                onSearch = { onEvent(ShowBookEvent.SearchClicked) },
                active = hasFocus,
                onActiveChange = {onEvent(ShowBookEvent.ChangeFocus(it)) },
                placeholder = { Text(stringResource(R.string.search_in_mesnevi)) },
                leadingIcon = {
                    if(hasFocus){
                        IconButton(onClick = {onEvent(ShowBookEvent.ChangeFocus(false))}){
                            Icon(Icons.Default.ArrowBack,contentDescription = null,)
                        }
                    }else{
                        Icon(Icons.Default.Search,
                            contentDescription = null)
                    }},
                trailingIcon = {
                    if(hasFocus){
                        IconButton(onClick = {onEvent(ShowBookEvent.ChangeQuery(""))}){
                            Icon(Icons.Default.Clear,contentDescription = null)
                        }
                    }
                },
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 5.dp, vertical = 9.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {

                    if(query.isNotBlank()){
                        item {
                            TextButton(
                                onClick = {
                                    onEvent(ShowBookEvent.SearchClicked)
                                },
                                modifier = Modifier.fillMaxWidth()
                            ){
                                Text(
                                    stringResource(R.string.item_click_for_searching,query),
                                )
                            }
                        }
                    }else{
                        items(histories){history->
                            HistoryItem(history,
                                onClick = {
                                    onEvent(ShowBookEvent.HistoryClicked(history))
                                },
                                onDeleteClick = {
                                    onEvent(ShowBookEvent.DeleteHistory(history))
                                },
                                modifier = Modifier.padding(vertical = 3.dp).fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

