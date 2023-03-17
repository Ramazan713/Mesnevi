package com.masterplus.mesnevi.core.presentation.dialog_body

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.constants.FontSizeEnum
import com.masterplus.mesnevi.core.presentation.extensions.increaseFontSize


@Composable
fun SelectFontSizeBottomContent(
    selected: FontSizeEnum? = null,
    onClickItem:(FontSizeEnum)->Unit,
    onClose: ()->Unit,
){

    val currentItem = rememberSaveable {
        mutableStateOf(selected)
    }

    Column {
        SelectRadioMenuItemBottomContent(
            items = FontSizeEnum.values().toList(),
            selected = selected,
            onClickItem = {
                onClickItem(it)
                currentItem.value = it
            },
            onClose = onClose,
            title = stringResource(R.string.select_font_size),
        )
        Text(
            stringResource(R.string.example_text),
            style = MaterialTheme.typography.bodyLarge
                .increaseFontSize(currentItem.value?.fontValue?:0),
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 30.dp),
            textAlign = TextAlign.Center
        )
    }
}