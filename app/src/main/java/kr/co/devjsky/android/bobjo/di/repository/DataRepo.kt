package kr.co.devjsky.android.bobjo.di.repository

import kr.co.devjsky.android.bobjo.R
import kr.co.devjsky.android.bobjo.data.model.remote.IFSchedule
import java.lang.Exception
import java.util.ArrayList
import java.util.Random

class DataRepo {

    var selected_schedule_info_idx:Int = -1
    var selected_schedule_info_data:IFSchedule.ScheduleInfo? = null

    var calendar_add_schedule_multi_check_list:ArrayList<String>? = null

    var schedule_sub_menu_selected_num:Int = -1





    fun getBottomMenuCoupleIconImage():Int{
        val bottomMenuCoupleIconImageArr:ArrayList<Int> = arrayListOf()
        bottomMenuCoupleIconImageArr.add(R.drawable.ico_heart_01)
        bottomMenuCoupleIconImageArr.add(R.drawable.ico_heart_02)
        bottomMenuCoupleIconImageArr.add(R.drawable.ico_heart_03)
        bottomMenuCoupleIconImageArr.add(R.drawable.ico_heart_04)
        bottomMenuCoupleIconImageArr.add(R.drawable.ico_heart_05)
        bottomMenuCoupleIconImageArr.add(R.drawable.ico_heart_06)
        return try {

            val minimumValue = 0
            val maximumValue = bottomMenuCoupleIconImageArr.size

            val random = Random()
            val randomValue: Int = random.nextInt(maximumValue - minimumValue + 1) + minimumValue
            bottomMenuCoupleIconImageArr[randomValue]
        }catch (e: Exception){
            bottomMenuCoupleIconImageArr[0]
        }

    }
}