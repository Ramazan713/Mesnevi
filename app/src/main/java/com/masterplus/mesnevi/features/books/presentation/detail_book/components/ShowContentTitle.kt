package com.masterplus.mesnevi.features.books.presentation.detail_book.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.constants.FontSizeEnum
import com.masterplus.mesnevi.features.books.domain.model.TitleNotes
import com.masterplus.mesnevi.core.presentation.dialog_body.ShowDialogContentNotes
import com.masterplus.mesnevi.core.presentation.extensions.increaseFontSize

@Composable
fun ShowContentTitle(
    titleNotes: TitleNotes,
    fontSize: FontSizeEnum,
    modifier: Modifier = Modifier,
){
    val shape = MaterialTheme.shapes.medium
    val notes = titleNotes.notes
    val isNotesVisible = rememberSaveable {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
            .clip(shape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 3.dp, vertical = 13.dp)
    ){

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 7.dp)
        ) {
            Text(
                titleNotes.title.content,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
                    .copy(fontWeight = FontWeight.W500)
                    .increaseFontSize(fontSize.fontValue)
            )
            if(notes.isNotEmpty()){
                IconButton(
                    onClick = {
                        isNotesVisible.value = true
                    }
                ){
                    Icon(
                        painterResource(R.drawable.ic_outline_info_24),
                        contentDescription = stringResource(R.string.n_info,titleNotes.title.id?:0),
                    )
                }
                ShowDialogContentNotes(
                    notes,
                    isVisible = isNotesVisible.value,
                    setVisible = {visible->
                        isNotesVisible.value = visible
                    }
                )
            }
        }

    }
}