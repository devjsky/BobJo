package kr.co.devjsky.android.bobjo.di

import kr.co.devjsky.android.bobjo.di.module.networkModule
import kr.co.devjsky.android.bobjo.di.module.repositoryModule
import kr.co.devjsky.android.bobjo.di.module.viewModelModule


// Gather all app modules
val appModule = listOf(
    repositoryModule,
    viewModelModule,
    networkModule
)