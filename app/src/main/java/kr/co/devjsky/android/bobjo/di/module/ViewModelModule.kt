package kr.co.devjsky.android.bobjo.di.module
import kr.co.devjsky.android.bobjo.ui.base.DialogViewModel
import kr.co.devjsky.android.bobjo.ui.schedule.viewmodel.ScheduleViewModel
import kr.co.devjsky.android.bobjo.ui.main.viewmodel.MainViewModel
import kr.co.devjsky.android.bobjo.ui.splash.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get(),get(),get()) }
    viewModel { MainViewModel(get(),get(),get()) }
    viewModel { ScheduleViewModel(get(),get(),get()) }
    viewModel { DialogViewModel(get(),get(),get()) }

}