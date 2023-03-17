package com.masterplus.mesnevi.features.search.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.features.books.domain.model.History


@Composable
fun HistoryItem(
    history: History, onClick: ()->Unit,
    onDeleteClick: ()->Unit, modifier: Modifier = Modifier,
){
    val shape = MaterialTheme.shapes.medium
    Row(
        modifier = modifier
            .clip(shape)
            .clickable { onClick() }
            .padding(horizontal = 1.dp)
            .padding(start = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(R.drawable.ic_baseline_history_24),
            contentDescription = null,
        )
        Text(
            history.content,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 10.dp).weight(1f)
        )
        IconButton(onClick = {
            onDeleteClick()
        }){
            Icon(Icons.Default.Close, contentDescription = null)
        }
    }
}