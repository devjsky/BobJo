package kr.co.devjsky.android.bobjo.di.module
import kr.co.devjsky.android.bobjo.di.repository.ApiRepo
import kr.co.devjsky.android.bobjo.di.repository.DataRepo
import kr.co.devjsky.android.bobjo.di.repository.UserRepo
import org.koin.dsl.module

val repositoryModule = module {

    single { ApiRepo(get(), get()) }
    single { UserRepo(get()) }
    single { DataRepo() }
}