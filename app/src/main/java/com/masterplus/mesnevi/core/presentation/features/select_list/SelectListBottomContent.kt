package com.masterplus.mesnevi.core.presentation.features.select_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.presentation.components.buttons.PrimaryButton
import com.masterplus.mesnevi.core.presentation.components.buttons.PrimaryLightButton
import com.masterplus.mesnevi.core.presentation.components.buttons.SecondaryLightButton
import com.masterplus.mesnevi.core.presentation.components.buttons.TertiaryLightButton
import com.masterplus.mesnevi.core.presentation.dialog_body.ShowGetTextDialog
import com.masterplus.mesnevi.features.list.domain.model.SelectableListView
import com.masterplus.mesnevi.core.presentation.features.select_list.components.SelectListItem

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalFoundationApi
@Composable
fun SelectListBottomContent(
    contentId: Int,
    items: List<SelectableListView>,
    onEvent: (SelectListEvent)->Unit
){

    val showTitleDialog = rememberSaveable{
        mutableStateOf(false)
    }

    Column {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = {onEvent(SelectListEvent.ShowModal(false))}){
                Icon(Icons.Default.Close,contentDescription = null)
            }
        }
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {

            item {
                SecondaryLightButton(
                    title = stringResource(R.string.add_list),
                    useBorder = true,
                    onClick = {showTitleDialog.value = true},
                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 5.dp)
                        .fillMaxWidth()
                )
            }

            item {
                if(items.isEmpty()){
                    Text(
                        stringResource(R.string.list_empty_text),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = 70.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            items(
                items,
            key = {item->item.listView.id?:0}
            ){item->
                SelectListItem(
                    selectableListView = item,
                    onChecked = {
                        onEvent(SelectListEvent.AddToList(contentId,item.listView))
                    },
                    modifier = Modifier.padding(horizontal = 3.dp,
                        vertical = 1.dp).fillMaxWidth()
                )
            }

        }
    }

    if(showTitleDialog.value){
        ShowGetTextDialog(
            title = stringResource(R.string.enter_title),
            onClosed = {showTitleDialog.value = false},
            onApproved = {onEvent(SelectListEvent.NewList(it))},
        )
    }

}


