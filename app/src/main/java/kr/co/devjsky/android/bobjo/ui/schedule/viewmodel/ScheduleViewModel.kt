package kr.co.devjsky.android.bobjo.ui.schedule.viewmodel

import android.widget.CompoundButton
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
    //var multiCheckAddFinLiveData = MutableLiveData<Boolean>()
    var pageClearLiveData = MutableLiveData<Boolean>()
    var allDayCheckLiveData = MutableLiveData<Boolean>()

    var scheduleAddFinLiveData = MutableLiveData<Boolean>()
    var scheduleModifyFinLiveData = MutableLiveData<Boolean>()
    var categoryGroupLiveData = MutableLiveData<String>()
    var tagColorLiveData = MutableLiveData<Int>()
    var stateLiveData = MutableLiveData<String>()

    var startDateLiveData = MutableLiveData<String>()
    var endDateLiveData = MutableLiveData<String>()
    var startTimeLiveData = MutableLiveData<String>()
    var endTimeLiveData = MutableLiveData<String>()
    var titleLiveData = MutableLiveData<String>()
    var contentLiveData = MutableLiveData<String>()
    var allDayLiveData = MutableLiveData<String>()

    var checkValidAlertLiveData = MutableLiveData<String>()
    init {
        pageClearLiveData.value = false
        testLiveData.value = ""
        multiCheckLiveData.value = false
      //  multiCheckAddFinLiveData.value = false
        allDayCheckLiveData.value = false
        categoryGroupLiveData.value = "MEMO"
        tagColorLiveData.value = 0
        stateLiveData.value = "ME"
        startDateLiveData.value = ""
        endDateLiveData.value = ""
        startTimeLiveData.value = ""
        endTimeLiveData.value = ""
        titleLiveData.value = ""
        contentLiveData.value = ""
        allDayLiveData.value = "N"
        checkValidAlertLiveData.value = ""
        scheduleAddFinLiveData.value = false
        scheduleModifyFinLiveData.value = false
    }



    fun getMonthSchedule(action: String,
                    year: String,
                    month: String){
        apiRepo.getMonthSchedule(action, year, month, object : ApiCallback {
            override fun <T> result(isSuccess: Boolean, code: Int, msg: String, data: T?) {
                if(isSuccess){

                    val resultData = data as IFSchedule
                    val scheduleInfo = resultData.schedule_info

                        scheduleLiveData.value = resultData





                }else{
                    Constants.LOG_E(TAG, "$TAG getMonthSchedule :: not Success")
                    scheduleLiveData.value = null
                }


            }
        })
    }

    fun addScheduleMulti(){
        if(checkAddScheduleMultiValid()) {
            val category_group = categoryGroupLiveData.value!!
            val title = titleLiveData.value!!
            val content = contentLiveData.value!!
            val state = stateLiveData.value!!
            val tagColor = tagColorLiveData.value!!

            val sb = StringBuilder()
            sb.append(dataRepo.calendar_add_schedule_multi_check_list!![0])
            for (i in 1 until dataRepo.calendar_add_schedule_multi_check_list!!.size){
                sb.append(",${dataRepo.calendar_add_schedule_multi_check_list!![i]}")
            }
            val dates = sb.toString()


            apiRepo.addScheduleMulti(
                "addScheduleMulti",
                dates,
                category_group,
                title,
                content,
                state,
                "N",
                "N",
                "N",
                "Y",
                tagColor,
                object : ApiCallback {
                    override fun <T> result(isSuccess: Boolean, code: Int, msg: String, data: T?) {

                     //   multiCheckAddFinLiveData.value = isSuccess
                        scheduleAddFinLiveData.value = isSuccess

                    }
                })
        }
    }
    fun addSchedule(){

        if(checkAddScheduleValid()){
            val startDate = "${startDateLiveData.value} ${startTimeLiveData.value}"
            var endDate = "${endDateLiveData.value} ${endTimeLiveData.value}"

            val category_group = categoryGroupLiveData.value!!
            val title = titleLiveData.value!!
            val content = contentLiveData.value!!
            val state = stateLiveData.value!!
            val allDay = allDayLiveData.value!!
            val tagColor = tagColorLiveData.value!!
            if(allDay == "Y"){
                endDate = startDate
            }

            apiRepo.addSchedule(
                "addSchedule",
                startDate,
                endDate,
                category_group,
                title,
                content,
                state,
                "N",
                "N",
                "N",
                allDay,
                tagColor,
                object : ApiCallback{
                    override fun <T> result(isSuccess: Boolean, code: Int, msg: String, data: T?) {

                        scheduleAddFinLiveData.value = isSuccess


                    }
                })
        }

    }
    fun modifySchedule(){

        if(checkModifyScheduleValid()){
            val startDate = "${startDateLiveData.value} ${startTimeLiveData.value}"
            var endDate = "${endDateLiveData.value} ${endTimeLiveData.value}"

            val category_group = categoryGroupLiveData.value!!
            val title = titleLiveData.value!!
            val content = contentLiveData.value!!
            val state = stateLiveData.value!!
            val allDay = allDayLiveData.value!!
            val tagColor = tagColorLiveData.value!!
            if(allDay == "Y"){
                endDate = startDate
            }
            if(dataRepo.selected_schedule_info_data == null){
                return
            }
            if(dataRepo.selected_schedule_info_data?.idx == null){
                return
            }
            apiRepo.modifySchedule(
                "modifySchedule",
                startDate,
                endDate,
                category_group,
                title,
                content,
                state,
                "N",
                "N",
                "N",
                allDay,
                tagColor,
                dataRepo.selected_schedule_info_data?.idx!!,
                object : ApiCallback{
                    override fun <T> result(isSuccess: Boolean, code: Int, msg: String, data: T?) {

                        scheduleModifyFinLiveData.value = isSuccess

                    }
                })
        }

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


    fun onSwitchCheckedChanged(switch: CompoundButton, checked: Boolean){

        allDayCheckLiveData.value = checked
        if(checked){
            allDayLiveData.value = "Y"
            startTimeLiveData.value = "00:00:00"
            endDateLiveData.value = "00:00:00"
        }else{
            allDayLiveData.value = "N"
            startTimeLiveData.value = ""
            endDateLiveData.value = ""
        }
    }

    fun checkAddScheduleValid():Boolean{
        var result = true


        if(titleLiveData.value == null || titleLiveData.value == ""){
            checkValidAlertLiveData.value = "제목을 입력해 주세요."
            result = false
        }
        else if(contentLiveData.value == null || contentLiveData.value == ""){
            checkValidAlertLiveData.value = "내용을 입력해 주세요."
            result = false
        }
        else if(startDateLiveData.value == null || startDateLiveData.value == ""){
            checkValidAlertLiveData.value = "시작 날짜를 입력해 주세요."
            result = false
        }
        else if(startTimeLiveData.value == null || startTimeLiveData.value == ""){
            if(allDayLiveData.value != null && allDayLiveData.value == "N"){
                checkValidAlertLiveData.value = "시작 시간을 입력해 주세요."
                result = false
            }

        }
        else if(endDateLiveData.value == null || endDateLiveData.value == ""){
            checkValidAlertLiveData.value = "종료 날짜를 입력해 주세요."
            result = false
        }
        else if(endTimeLiveData.value == null || endTimeLiveData.value == ""){
            if(allDayLiveData.value != null && allDayLiveData.value == "N"){
                checkValidAlertLiveData.value = "종료 시간을 입력해 주세요."
                result = false
            }

        }


        return result

    }
    fun checkAddScheduleMultiValid():Boolean{
        var result = true


        if(titleLiveData.value == null || titleLiveData.value == ""){
            checkValidAlertLiveData.value = "제목을 입력해 주세요."
            result = false
        }
        else if(contentLiveData.value == null || contentLiveData.value == ""){
            checkValidAlertLiveData.value = "내용을 입력해 주세요."
            result = false
        }



        return result

    }
    fun checkModifyScheduleValid():Boolean{
        var result = true


        if(titleLiveData.value == null || titleLiveData.value == ""){
            checkValidAlertLiveData.value = "제목을 입력해 주세요."
            result = false
        }
        else if(contentLiveData.value == null || contentLiveData.value == ""){
            checkValidAlertLiveData.value = "내용을 입력해 주세요."
            result = false
        }
        else if(startDateLiveData.value == null || startDateLiveData.value == ""){
            checkValidAlertLiveData.value = "시작 날짜를 입력해 주세요."
            result = false
        }
        else if(startTimeLiveData.value == null || startTimeLiveData.value == ""){
            if(allDayLiveData.value != null && allDayLiveData.value == "N"){
                checkValidAlertLiveData.value = "시작 시간을 입력해 주세요."
                result = false
            }

        }
        else if(endDateLiveData.value == null || endDateLiveData.value == ""){
            checkValidAlertLiveData.value = "종료 날짜를 입력해 주세요."
            result = false
        }
        else if(endTimeLiveData.value == null || endTimeLiveData.value == ""){
            if(allDayLiveData.value != null && allDayLiveData.value == "N"){
                checkValidAlertLiveData.value = "종료 시간을 입력해 주세요."
                result = false
            }

        }


        return result

    }
}