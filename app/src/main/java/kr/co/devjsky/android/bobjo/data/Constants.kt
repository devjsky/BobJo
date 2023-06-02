package kr.co.devjsky.android.bobjo.data

import android.util.Log

interface Constants {

    companion object{
        const val DEBUG_MODE = true
        const val LOG_MODE = true
        const val API_SERVER_URL = "http://devjsky.cafe24.com/bobjo/api/"

        const val FRAGMENT_MAIN_DASHBOARD = "FRAGMENT_MAIN_DASHBOARD"
        const val FRAGMENT_MAIN_COUPLE = "FRAGMENT_MAIN_COUPLE"
        const val FRAGMENT_MAIN_SCHEDULE = "FRAGMENT_MAIN_SCHEDULE"
        const val FRAGMENT_MAIN_STORY = "FRAGMENT_MAIN_STORY"
        const val FRAGMENT_MAIN_SPENDING = "FRAGMENT_MAIN_SPENDING"
        const val FRAGMENT_MAIN_MY_PAGE = "FRAGMENT_MAIN_MY_PAGE"



        fun LOG_D(tag: String, msg: String){
            if(LOG_MODE){
                Log.d(tag, msg)
            }

        }
        fun LOG_E(tag: String, msg: String){
            if(LOG_MODE){
                Log.e(tag, msg)
            }

        }
        fun LOG_I(tag: String, msg: String){
            if(LOG_MODE){
                Log.i(tag, msg)
            }

        }

    }
}