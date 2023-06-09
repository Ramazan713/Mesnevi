package com.masterplus.mesnevi.core.presentation.dialog_body

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun CustomDialog(
    onClosed: ()->Unit,
    modifier: Modifier = Modifier,
    allowDismiss: Boolean = true,
    content: @Composable () -> Unit,
){
    Dialog(
        onDismissRequest = onClosed,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = allowDismiss,
            dismissOnClickOutside = allowDismiss
        )
    ){
        Surface(
            modifier = modifier.padding(horizontal = 5.dp)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium),
            color = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ){
            content()
        }
    }
}