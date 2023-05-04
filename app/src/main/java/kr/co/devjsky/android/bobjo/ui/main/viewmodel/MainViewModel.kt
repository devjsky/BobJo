package kr.co.devjsky.android.bobjo.ui.main.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import kr.co.devjsky.android.bobjo.api.ApiCallback
import kr.co.devjsky.android.bobjo.api.ResponseCode
import kr.co.devjsky.android.bobjo.data.Constants
import kr.co.devjsky.android.bobjo.data.Constants.Companion.FRAGMENT_MAIN_DASHBOARD
import kr.co.devjsky.android.bobjo.data.Constants.Companion.LOG_D
import kr.co.devjsky.android.bobjo.data.Constants.Companion.LOG_E
import kr.co.devjsky.android.bobjo.data.model.remote.IFSchedule
import kr.co.devjsky.android.bobjo.di.repository.ApiRepo
import kr.co.devjsky.android.bobjo.di.repository.DataRepo
import kr.co.devjsky.android.bobjo.di.repository.UserRepo
import kr.co.devjsky.android.bobjo.ui.base.BaseViewModel
import kr.co.devjsky.android.bobjo.ui.main.activity.MainActivity
import kr.co.soundleader.android.lesson1vs5.data.model.api.IFDefault
import kr.co.soundleader.android.lesson1vs5.data.model.api.IFTEST
import retrofit2.http.Field
import java.text.SimpleDateFormat
import java.util.Calendar

class MainViewModel(private val apiRepo: ApiRepo, private val userRepo:UserRepo, private val dataRepo: DataRepo) : BaseViewModel() {
    var bottomMenuLiveData = MutableLiveData<String>()
    var testDataLiveData= MutableLiveData<String>()
    var mainActivity:MainActivity? = null

    var scheduleLiveData = MutableLiveData<IFSchedule>()

    init {
        bottomMenuLiveData.value = FRAGMENT_MAIN_DASHBOARD

    }

    fun setBottomMenu(menuTitle : String){
        bottomMenuLiveData.postValue(menuTitle)
    }


    fun calculateDDay(){
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        val startDate = dateFormat.parse("2014-09-18").time
        val endDate = dateFormat.parse("2023-04-07").time
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time.time

        val dday = ((today - startDate) / (24 * 60 * 60 * 1000))+1
        testDataLiveData.value = dday.toString()
    }


}