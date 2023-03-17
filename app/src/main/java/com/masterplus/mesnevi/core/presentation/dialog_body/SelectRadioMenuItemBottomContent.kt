package com.masterplus.mesnevi.core.presentation.dialog_body

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.masterplus.mesnevi.core.domain.constants.IMenuItemEnum
import com.masterplus.mesnevi.core.presentation.components.RadioItem


@Composable
fun <T: IMenuItemEnum> SelectRadioMenuItemBottomContent(
    items: List<T>,
    selected: T? = null,
    onClickItem:(T)->Unit,
    onClose: ()->Unit,
    title: String? = null
){

    val currentItem = rememberSaveable {
        mutableStateOf(selected)
    }

    LazyColumn(
        modifier = Modifier.padding(bottom = 13.dp, top = 3.dp)
            .padding(horizontal = 13.dp),
    ) {

        item {
            if(title!=null){
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        title,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = 5.dp, horizontal = 35.dp),
                        textAlign = TextAlign.Center
                    )
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(onClick = {onClose()}){
                            Icon(Icons.Default.Close, contentDescription = null)
                        }
                    }
                }
            }
        }
        items(items){item->
            RadioItem(
                title = item.title.asString(),
                isSelected = currentItem.value == item,
                onClick = {
                    currentItem.value = item
                    onClickItem(item)
                },
                modifier = Modifier.padding(vertical = 5.dp)
            )
        }
    }
}