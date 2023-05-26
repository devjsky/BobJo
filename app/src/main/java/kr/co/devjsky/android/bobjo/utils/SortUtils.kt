package kr.co.devjsky.android.bobjo.utils

import java.text.SimpleDateFormat
import java.util.Collections
import java.util.Date

class SortUtils {
    companion object {
        fun sortStringDateList(stringDateList:ArrayList<String>): ArrayList<String> {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val sortedDateList = ArrayList<Date>()
            for(stringDate in stringDateList){
                val date = sdf.parse(stringDate)
                sortedDateList.add(date)
            }
            sortedDateList.sort()
            val sortedStringDateList = ArrayList<String>()
            for(sortedDate in sortedDateList){
                sortedStringDateList.add(sdf.format(sortedDate))
            }
            return sortedStringDateList
        }

        fun sortReversedStringDateList(stringDateList:ArrayList<String>): ArrayList<String> {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val sortedDateList = ArrayList<Date>()
            for(stringDate in stringDateList){
                val date = sdf.parse(stringDate)
                sortedDateList.add(date)
            }
            sortedDateList.sortWith(Collections.reverseOrder())
            val sortedStringDateList = ArrayList<String>()
            for(sortedDate in sortedDateList){
                sortedStringDateList.add(sdf.format(sortedDate))
            }
            return sortedStringDateList
        }
    }

}