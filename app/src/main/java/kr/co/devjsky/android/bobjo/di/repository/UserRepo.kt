package kr.co.devjsky.android.bobjo.di.repository

import kr.co.devjsky.android.bobjo.api.ApiService
import kr.co.devjsky.android.bobjo.data.model.local.UserInfo


class UserRepo(private val apiService: ApiService) {
    var fcmToken: String? = null
    var userInfo: UserInfo? = null

    fun isLogin(): Boolean{
        return userInfo != null && userInfo!!.userToken != null && userInfo!!.userToken != ""
    }

    fun setUserLogout(){
        userInfo = null
    }



}