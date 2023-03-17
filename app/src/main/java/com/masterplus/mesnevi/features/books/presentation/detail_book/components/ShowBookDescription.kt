package com.masterplus.mesnevi.features.books.presentation.detail_book.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.masterplus.mesnevi.core.domain.constants.FontSizeEnum
import com.masterplus.mesnevi.core.presentation.extensions.increaseFontSize
import com.masterplus.mesnevi.features.books.domain.model.Book


@Composable
fun ShowBookDescription(
    book: Book,
    modifier: Modifier = Modifier,
    fontSize: FontSizeEnum,
){
    val shape = MaterialTheme.shapes.medium
    Column(
        modifier = modifier.clip(shape)
            .background(MaterialTheme.colorScheme.surfaceVariant,shape)
            .padding(horizontal = 7.dp, vertical = 7.dp)
    ) {
        Text(
            book.name,
            style = MaterialTheme.typography.titleLarge
                .increaseFontSize(fontSize.fontValue),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Text(
            book.description,
            style = MaterialTheme.typography.bodyLarge
                .increaseFontSize(fontSize.fontValue),
        )
    }
}