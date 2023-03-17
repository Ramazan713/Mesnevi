package com.masterplus.mesnevi.core.presentation.extensions

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*


@Composable
fun LazyListState.visibleMiddlePosition(): Int{
    return remember {
        derivedStateOf {
            (
                    firstVisibleItemIndex +
                    (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0)
            ) / 2
        }
    }.value
}

@Composable
fun LazyListState.hasScrolledToTop(): Boolean{
    return remember(this){
        derivedStateOf {
            firstVisibleItemIndex == 0
        }
    }.value
}

@Composable
fun LazyListState.hasScrolledToEnd(): Boolean{
    return remember(this){
        derivedStateOf {
            layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
        }
    }.value
}
@Composable
fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    val hasScrollEnd = hasScrolledToEnd()
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex && hasScrollEnd
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}
