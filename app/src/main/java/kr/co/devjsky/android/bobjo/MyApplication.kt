package kr.co.devjsky.android.bobjo

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.telecom.PhoneAccount
import android.telecom.PhoneAccountHandle
import android.telecom.TelecomManager
import kr.co.devjsky.android.bobjo.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MyApplication: Application() {

    companion object {
        private lateinit var myApplication: MyApplication
        fun getInstance() : MyApplication = myApplication
    }
    private var phoneAccount: PhoneAccount? = null
    override fun onCreate() {
        super.onCreate()
        startKoin() {
            androidContext(this@MyApplication)
            modules(appModule)
        }
        myApplication = this







    }


}




