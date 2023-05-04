package kr.co.devjsky.android.bobjo.api

interface ApiCallback {

    fun <T> result(isSuccess: Boolean, code: Int, msg: String, data: T?)

}