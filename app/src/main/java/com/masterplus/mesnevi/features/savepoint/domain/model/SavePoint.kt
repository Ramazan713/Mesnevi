package com.masterplus.mesnevi.features.savepoint.domain.model

import com.masterplus.mesnevi.core.domain.constants.AutoType
import com.masterplus.mesnevi.core.domain.constants.DateFormatEnum
import com.masterplus.mesnevi.core.domain.constants.SavePointType
import com.masterplus.mesnevi.core.domain.util.DateFormatHelper
import java.text.SimpleDateFormat
import java.util.*

data class SavePoint(
    val id: Int?,
    val title: String,
    val itemPosIndex: Int,
    val savePointType: SavePointType,
    val modifiedDate: Calendar,
    val autoType: AutoType
){

    fun getReadableDate(): String{
        return DateFormatHelper.getReadableDate(modifiedDate.time,DateFormatEnum.readable)
    }

    companion object{

        fun getTitle(shortTitle: String,
                     autoType: AutoType = AutoType.Default,
                     dateParam: Calendar? = null
        ): String{
            val date = dateParam ?: Calendar.getInstance()
            val readableDate = DateFormatHelper.getReadableDate(date.time,DateFormatEnum.readable)
            val buildTitle = buildString {
                autoType.label?.let { label->
                    append("$label - ")
                }
                append("$shortTitle - ")
                append(readableDate)
            }
            return buildTitle
        }
    }

}
