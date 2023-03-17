package com.masterplus.mesnevi.core.presentation.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.data.K
import com.masterplus.mesnevi.core.domain.model.ContentModel
import com.masterplus.mesnevi.core.domain.util.ToastHelper
import com.masterplus.mesnevi.core.domain.util.UiText
import com.masterplus.mesnevi.features.books.domain.model.BookInfoEnum


fun ContentModel.shareText(context: Context,bookId: Int){
    val bookTitle = BookInfoEnum.fromBookId(bookId).title.asString(context)
    val shareContent = "$content ( $bookTitle - $itemNumber)"
    shareContent.shareText(context)
}

fun ContentModel.copyText(context: Context,bookId: Int,clipboardManager: ClipboardManager){
    val bookTitle = BookInfoEnum.fromBookId(bookId).title.asString(context)
    val shareContent = "$content ( $bookTitle - $itemNumber)"
    clipboardManager.setText(AnnotatedString(shareContent))
    ToastHelper.showMessage(UiText.Resource(R.string.copied),context)
}

fun ContentModel.shareLink(context: Context,bookId: Int, pos: Int){
    val text = "${K.shareLinkUrl}/$bookId/$pos"
    text.shareText(context)
}

fun String.shareText(context: Context){
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, this@shareText)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}

fun Context.shareApp(){
    val url = "http://play.google.com/store/apps/details?id=$packageName"
    url.shareText(this)
}

fun Context.launchPlayApp(){
    val url = "http://play.google.com/store/apps/details?id=$packageName"
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
        setPackage("com.android.vending")
    }
    startActivity(intent)
}

