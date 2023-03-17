package com.masterplus.mesnevi.core.presentation.components

import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.TextView
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.constants.FontSizeEnum
import com.masterplus.mesnevi.core.domain.model.ContentNotes
import com.masterplus.mesnevi.core.presentation.dialog_body.ShowDialogContentNotes
import com.masterplus.mesnevi.core.presentation.extensions.increaseFontSize
import com.masterplus.mesnevi.core.presentation.extensions.toGraphicsColor
import java.util.jar.Attributes


@Composable
fun ShowContentNotes(
    contentNotes: ContentNotes,
    modifier: Modifier = Modifier,
    pos: Int = 0,
    useBorder: Boolean = true,
    showPos: Boolean = false,
    fontSize: FontSizeEnum,
    onLongClick: (()->Unit)? = null,
){
    val content = contentNotes.contentModel
    ShowContentNotesBase(
        contentNotes,
        modifier,
        pos = pos,
        fontSize = fontSize,
        useBorder= useBorder,
        showPos = showPos,
        onLongClick = onLongClick
    ){
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontStyle = FontStyle.Italic,)){
                    append(content.itemNumber.toString() + " - ")
                }
                append(content.content)
            },
            style = MaterialTheme.typography.bodyLarge
                .increaseFontSize(fontSize.fontValue),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ShowContentNotesSearchable(
    contentNotes: ContentNotes,
    pos: Int,
    modifier: Modifier = Modifier,
    showPos: Boolean = true,
    fontSize: FontSizeEnum,
    useBorder: Boolean = true,
    onLongClick: (()->Unit)? = null,
){
    val contentModel = contentNotes.contentModel

    val content = """${contentModel.itemNumber} - ${contentModel.content}"""

    val color = LocalContentColor.current.toGraphicsColor()

    ShowContentNotesBase(
        contentNotes = contentNotes,
        modifier = modifier,
        pos = pos,
        fontSize = fontSize,
        useBorder = useBorder,
        showPos = showPos,
        onLongClick = onLongClick
    ){
        val style = MaterialTheme.typography.bodyLarge
            .increaseFontSize(fontSize.fontValue)
        AndroidView(
            factory = { context ->
                TextView(context).apply {
                    textSize = style.fontSize.value
                    setTextColor(color)
                } },
            update = { it.text = HtmlCompat.fromHtml(content, HtmlCompat.FROM_HTML_MODE_COMPACT) },
            modifier = Modifier.weight(1f)
        )
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ShowContentNotesBase(
    contentNotes: ContentNotes,
    modifier: Modifier = Modifier,
    pos: Int = 0,
    fontSize: FontSizeEnum,
    showPos: Boolean = false,
    useBorder: Boolean = true,
    onLongClick: (()->Unit)? = null,
    contentComposable: @Composable RowScope.()->Unit
){
    val notes = contentNotes.notes
    val shape = MaterialTheme.shapes.medium
    val isNotesVisible = rememberSaveable {
        mutableStateOf(false)
    }

    Box(modifier = modifier
        .clip(shape)
        .then(if(useBorder) Modifier.border(2.dp, MaterialTheme.colorScheme.outline,shape) else Modifier)
        .background(MaterialTheme.colorScheme.surfaceVariant)
        .combinedClickable(
            onClick = {},
            onLongClick = { onLongClick?.let { it() } })
        .padding(horizontal = 7.dp)
        .padding(top = if(showPos) 1.dp else 7.dp),
    ){

        Column {
            if(showPos){
                Text(
                    "$pos",
                    modifier = Modifier.fillMaxWidth().padding(bottom = 1.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                        .increaseFontSize(fontSize.fontValue)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 7.dp)
            ) {

                contentComposable()
                if(notes.isNotEmpty()){
                    IconButton(
                        onClick = {
                            isNotesVisible.value = true
                        }
                    ){
                        Icon(
                            painterResource(R.drawable.ic_outline_info_24),
                            contentDescription = stringResource(R.string.n_info,contentNotes.contentModel.itemNumber),
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


}