package com.masterplus.mesnevi.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.constants.IMenuItemEnum


@Composable
fun <T: IMenuItemEnum>CustomDropdownMenu(
    modifier: Modifier = Modifier,
    items: List<T>,
    onItemChange: ((T)->Unit)? = null,
    currentItem: T? = null,
    showIcons: Boolean = false
){

    val context = LocalContext.current
    val shape = MaterialTheme.shapes.small

    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    var currentText by rememberSaveable {
        mutableStateOf("")
    }

    LaunchedEffect(Unit){
        currentText = currentItem?.title?.asString(context) ?:
                items.firstOrNull()?.title?.asString(context) ?: ""
    }


    Column(
        modifier = modifier.padding(5.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){
        Row(
            modifier = modifier
                .clip(shape = shape)
                .background(Color.Transparent)
                .border(2.dp, MaterialTheme.colorScheme.outline,shape)
                .clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {
                    expanded = true
                }
                .padding(horizontal = 11.dp, vertical = 9.dp)
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(currentText, modifier = Modifier.padding(horizontal = 10.dp))
            Icon(painter = painterResource(R.drawable.ic_baseline_arrow_drop_down_24),
                contentDescription = stringResource(R.string.dropdown_menu_text), )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false},
            modifier = modifier
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(horizontal = 3.dp),
        ){
            items.forEach { item->
                val title = item.title.asString(context)
                DropdownMenuItem(
                    text = { Text(title, color = LocalContentColor.current) },
                    colors = MenuDefaults.itemColors(
                        textColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    onClick = {
                        currentText = item.title.asString(context)
                        expanded = false
                        onItemChange?.invoke(item)
                    },
                    modifier = Modifier.clip(shape),
                    leadingIcon = if(!showIcons) null else {
                        {item.iconInfo?.let { iconInfo->
                            Icon(painterResource(iconInfo.drawableId),
                                contentDescription = stringResource(R.string.n_menu_item,title),
                                tint = iconInfo.tintColor ?: LocalContentColor.current,
                            ) }}
                    }
                )
            }
        }
    }
}