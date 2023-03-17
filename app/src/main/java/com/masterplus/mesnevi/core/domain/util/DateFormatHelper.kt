package com.masterplus.mesnevi.core.domain.util

import android.util.Log
import com.masterplus.mesnevi.core.domain.constants.DateFormatEnum
import java.text.SimpleDateFormat
import java.util.*

object DateFormatHelper {

    fun getReadableDate(date: Date, formatEnum: DateFormatEnum): String{
        return SimpleDateFormat(formatEnum.format, Locale.getDefault()).format(date.time)
    }

    fun getReadableDate(milliSeconds: Long,formatEnum: DateFormatEnum): String{
        val date = Calendar.getInstance().time.apply { time = milliSeconds }
        return getReadableDate(date,formatEnum)
    }
}