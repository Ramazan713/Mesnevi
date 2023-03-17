package com.masterplus.mesnevi.core.presentation.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.masterplus.mesnevi.MainActivity


fun Color.toGraphicsColor(): Int{
    toArgb().let {
        return android.graphics.Color.argb(it.alpha,it.red,it.green,it.blue)
    }
}

fun TextStyle.increaseFontSize(value: Int): TextStyle {
    val newFontSizeValue = fontSize.value + value.toFloat()
    return copy(
        fontSize = newFontSizeValue.sp,
        letterSpacing = (letterSpacing.value / newFontSizeValue).em,
        lineHeight = (1.5 * newFontSizeValue).sp
    )
}

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
fun Context.refreshApp(){
    val activity = this as Activity
    activity.finish()
    activity.startActivity(Intent(activity, MainActivity::class.java))
}