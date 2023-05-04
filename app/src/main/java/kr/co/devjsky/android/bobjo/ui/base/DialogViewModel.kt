package kr.co.devjsky.android.bobjo.ui.base

import kr.co.devjsky.android.bobjo.di.repository.ApiRepo
import kr.co.devjsky.android.bobjo.di.repository.DataRepo
import kr.co.devjsky.android.bobjo.di.repository.UserRepo

class DialogViewModel(private val apiRepo: ApiRepo, private val userRepo: UserRepo, private val dataRepo: DataRepo): BaseViewModel() {
}