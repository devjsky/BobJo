package kr.co.devjsky.android.bobjo.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.text.DecimalFormat
import java.util.regex.Matcher
import java.util.regex.Pattern
class DateUtils {
    companion object {
        fun getDateString(date:String, format : String): String {
            val mDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date)
            return SimpleDateFormat(format).format(mDate)
        }

        fun getTimeString(time:String, format : String): String {
            val mTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time)
            return SimpleDateFormat(format).format(mTime)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun changeDateFormat(dateTime:String, beforeFormat: String, afterFormat: String): String{
            return if(dateTime != null && dateTime != "" && dateTime != "null"){
                val parsedLocalDateTime =
                    LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(beforeFormat, Locale.KOREA))
                val YMD = parsedLocalDateTime.format(DateTimeFormatter.ofPattern(afterFormat, Locale.KOREA))
                YMD
            }else{
                ""
            }
        }


    }

}