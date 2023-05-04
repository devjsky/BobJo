package kr.co.devjsky.android.bobjo

import android.app.Application
import kr.co.devjsky.android.bobjo.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MyApplication: Application() {

    companion object {
        private lateinit var myApplication: MyApplication
        fun getInstance() : MyApplication = myApplication
    }
    override fun onCreate() {
        super.onCreate()
        startKoin() {
            androidContext(this@MyApplication)
            modules(appModule)
        }
        myApplication = this







    }



}




