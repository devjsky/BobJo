package kr.co.devjsky.android.bobjo.di.repository

import kr.co.devjsky.android.bobjo.api.ApiCallback
import kr.co.devjsky.android.bobjo.api.ApiService
import kr.co.devjsky.android.bobjo.api.ResponseCode
import kr.co.devjsky.android.bobjo.data.Constants.Companion.DEBUG_MODE
import kr.co.devjsky.android.bobjo.data.Constants.Companion.LOG_D
import kr.co.devjsky.android.bobjo.data.model.remote.IFSchedule
import kr.co.soundleader.android.lesson1vs5.data.model.api.IFDefault
import kr.co.soundleader.android.lesson1vs5.data.model.api.IFTEST
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field

class ApiRepo(private val apiService: ApiService, private val userRepo: UserRepo) {

    val TAG = this.javaClass.simpleName + "_LOG"

    fun getIFTEST(callback: ApiCallback){

        var user_token = ""
        if(DEBUG_MODE){
            user_token = "SgY1HQHJGZHJPteebUenL8JGRl58vzbeGZ6eGENctlM9FQdHBhUdguJYGFW24X"
        }else{
            user_token = userRepo.userInfo?.userToken + ""
        }
        apiService.getIFTEST(user_token).enqueue(object : Callback<IFTEST> {
            override fun onResponse(call: Call<IFTEST>, response: Response<IFTEST>) {
                LOG_D(TAG, "IFTEST isSuccessful")
                if (response.isSuccessful) {
                    response.body()?.let {

                        val data: IFTEST = response.body()!!
                        val code: Int = data.header?.code!!
                        val msg: String = data.header?.message+""

                        if(code == ResponseCode.OK){

                            callback.result(true, code, msg, data)

                        }else{
                            callback.result(false, code, msg, null)
                        }

                    }
                }
            }

            override fun onFailure(call: Call<IFTEST>, t: Throwable) {
                LOG_D(TAG, "IFTEST onFailure : " + t.toString())
                callback.result(false, ResponseCode.API_ERROR, t.toString(), null)
            }
        })

    }

    fun getSchedule(action: String,
                    year: String,
                    month: String,
                    share_user_idx: String,
                    callback: ApiCallback){

        var user_token = ""
        if(DEBUG_MODE){
            user_token = "test"
        }else{
            user_token = userRepo.userInfo?.userToken + ""
        }
        apiService.getSchedule(user_token, action, year, month, share_user_idx).enqueue(object : Callback<IFSchedule> {
            override fun onResponse(call: Call<IFSchedule>, response: Response<IFSchedule>) {
                LOG_D(TAG, "getSchedule isSuccessful")
                if (response.isSuccessful) {
                    response.body()?.let {

                        val data: IFSchedule = response.body()!!
                        val code: Int = data.header?.code!!
                        val msg: String = data.header?.message+""

                        if(code == ResponseCode.OK){

                            callback.result(true, code, msg, data)

                        }else{
                            callback.result(false, code, msg, null)
                        }

                    }
                }
            }

            override fun onFailure(call: Call<IFSchedule>, t: Throwable) {
                LOG_D(TAG, "getSchedule onFailure : " + t.toString())
                callback.result(false, ResponseCode.API_ERROR, t.toString(), null)
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
                         allday: String,
                         tag_color: Int,
                         callback: ApiCallback){

        var user_token = ""
        if(DEBUG_MODE){
            user_token = "test"
        }else{
            user_token = userRepo.userInfo?.userToken + ""
        }
        apiService.addScheduleMulti(user_token, action, dates, category_group, title, content, state, check_state, top, bigday, allday, tag_color).enqueue(object : Callback<IFDefault> {
            override fun onResponse(call: Call<IFDefault>, response: Response<IFDefault>) {
                LOG_D(TAG, "addScheduleMulti isSuccessful")
                if (response.isSuccessful) {
                    response.body()?.let {

                        val data: IFDefault = response.body()!!
                        val code: Int = data.header?.code!!
                        val msg: String = data.header?.message+""

                        if(code == ResponseCode.OK){

                            callback.result(true, code, msg, data)

                        }else{
                            callback.result(false, code, msg, null)
                        }

                    }
                }
            }

            override fun onFailure(call: Call<IFDefault>, t: Throwable) {
                LOG_D(TAG, "addScheduleMulti onFailure : " + t.toString())
                callback.result(false, ResponseCode.API_ERROR, t.toString(), null)
            }
        })

    }
    fun removeSchedule(action: String,
                         schedule_idx: Int,
                         callback: ApiCallback){

        var user_token = ""
        if(DEBUG_MODE){
            user_token = "test"
        }else{
            user_token = userRepo.userInfo?.userToken + ""
        }
        apiService.removeSchedule(user_token, action, schedule_idx).enqueue(object : Callback<IFDefault> {
            override fun onResponse(call: Call<IFDefault>, response: Response<IFDefault>) {
                LOG_D(TAG, "addScheduleMulti isSuccessful")
                if (response.isSuccessful) {
                    response.body()?.let {

                        val data: IFDefault = response.body()!!
                        val code: Int = data.header?.code!!
                        val msg: String = data.header?.message+""

                        if(code == ResponseCode.OK){

                            callback.result(true, code, msg, data)

                        }else{
                            callback.result(false, code, msg, null)
                        }

                    }
                }
            }

            override fun onFailure(call: Call<IFDefault>, t: Throwable) {
                LOG_D(TAG, "addScheduleMulti onFailure : " + t.toString())
                callback.result(false, ResponseCode.API_ERROR, t.toString(), null)
            }
        })

    }
}