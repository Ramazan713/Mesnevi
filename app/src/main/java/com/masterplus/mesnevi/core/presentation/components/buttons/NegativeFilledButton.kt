package com.masterplus.mesnevi.core.presentation.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NegativeFilledButton(
    title: String,
    onClick: ()->Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    useBorder: Boolean = false,
    borderStroke: BorderStroke = BorderStroke(2.dp,MaterialTheme.colorScheme.outline)
){

    ButtonBase(
        onClick = onClick,
        title = title,
        isEnabled = isEnabled,
        modifier = modifier,
        useBorder = useBorder,
        borderStroke = borderStroke,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error,
            contentColor = MaterialTheme.colorScheme.onError
        )
    )
}