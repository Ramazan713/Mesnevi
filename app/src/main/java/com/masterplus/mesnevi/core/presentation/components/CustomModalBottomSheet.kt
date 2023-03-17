package com.masterplus.mesnevi.core.presentation.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.masterplus.mesnevi.core.presentation.features.select_list.SelectListEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomModalBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetStateParam: SheetState? = null,
    skipHalfExpanded: Boolean = false,
    content: @Composable() (ColumnScope.() -> Unit)
){

    val func = remember {
        mutableStateOf(object: (SheetValue)->Boolean{
            override fun invoke(it: SheetValue): Boolean {
                if(it == SheetValue.Hidden){
                    onDismissRequest()
                }
                return true
            }
        })
    }

    val sheetState = sheetStateParam ?: rememberSheetState(
        skipHalfExpanded = skipHalfExpanded,
        confirmValueChange = func.value
    )
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ){
        content()
    }
}