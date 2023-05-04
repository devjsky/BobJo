package kr.co.devjsky.android.bobjo.ui.schedule.viewmodel

import androidx.lifecycle.MutableLiveData
import kr.co.devjsky.android.bobjo.api.ApiCallback
import kr.co.devjsky.android.bobjo.api.ResponseCode
import kr.co.devjsky.android.bobjo.data.Constants
import kr.co.devjsky.android.bobjo.data.Constants.Companion.LOG_D
import kr.co.devjsky.android.bobjo.data.model.remote.IFSchedule
import kr.co.devjsky.android.bobjo.di.repository.ApiRepo
import kr.co.devjsky.android.bobjo.di.repository.DataRepo
import kr.co.devjsky.android.bobjo.di.repository.UserRepo
import kr.co.devjsky.android.bobjo.ui.base.BaseViewModel
import kr.co.soundleader.android.lesson1vs5.data.model.api.IFDefault

class ScheduleViewModel(private val apiRepo: ApiRepo, private val userRepo:UserRepo, private val dataRepo:DataRepo): BaseViewModel() {
    var testLiveData = MutableLiveData<String?>()
    var multiCheckLiveData = MutableLiveData<Boolean>()
    var scheduleLiveData = MutableLiveData<IFSchedule?>()
    var multiCheckAddFinLiveData = MutableLiveData<Boolean>()
    var eventListClearLiveData = MutableLiveData<Boolean>()
    init {
        eventListClearLiveData.value = false
        testLiveData.value = ""
        multiCheckLiveData.value = false
        multiCheckAddFinLiveData.value = false
    }



    fun getSchedule(action: String,
                    year: String,
                    month: String,
                    share_user_idx: String){
        apiRepo.getSchedule(action, year, month, share_user_idx, object : ApiCallback {
            override fun <T> result(isSuccess: Boolean, code: Int, msg: String, data: T?) {
                if(isSuccess){

                    val resultData = data as IFSchedule
                    val scheduleInfo = resultData.schedule_info

                        scheduleLiveData.value = resultData





                }else{
                    Constants.LOG_E(TAG, "$TAG getSchedule :: not Success")
                    scheduleLiveData.value = null
                }


            }
        })
    }

    fun addScheduleMulti(action: String,
                         dates: String,
                         category_group: String,
                         title: String,
                         content: String,
                         state: String,
                         check_state: String,
                         top: String,
                         bigday: String,
                         allday: String){
        apiRepo.addScheduleMulti(action, dates, category_group, title, content, state, check_state, top, bigday, allday, object : ApiCallback{
            override fun <T> result(isSuccess: Boolean, code: Int, msg: String, data: T?) {

                multiCheckAddFinLiveData.value = isSuccess


            }
        })
    }
    fun removeSchedule(action: String,
                         schedule_idx: Int,
    apiCallback : ApiCallback){
        LOG_D(TAG, "removeSchedule() -> schedule_idx:${schedule_idx}")
        apiRepo.removeSchedule(action, schedule_idx, object : ApiCallback{
            override fun <T> result(isSuccess: Boolean, code: Int, msg: String, data: T?) {
                apiCallback.result(isSuccess, code, msg, data)


            }
        })
    }
}