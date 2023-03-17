package com.masterplus.mesnevi.core.presentation.dialog_body

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.model.NoteModel
import com.masterplus.mesnevi.core.presentation.components.buttons.PrimaryButton

@Composable
fun ShowDialogContentNotes(
    notes: List<NoteModel>,
    isVisible: Boolean,
    setVisible: (Boolean)->Unit,
    onClick: (()->Unit)? = null
){
    if(isVisible){
        AlertDialog(
            onDismissRequest = {
                setVisible(false)
            },
            title = { Text(
                stringResource(R.string.notes),
                style = MaterialTheme.typography.titleMedium
            ) },
            text = {
                LazyColumn {
                    items(notes){note->
                        Text("* ${note.content}",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                }
            },
            confirmButton = {
                PrimaryButton(
                    title = stringResource(R.string.approve),
                    onClick = {setVisible(false)}
                )
            }
        )
    }
}