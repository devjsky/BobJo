package kr.co.devjsky.android.bobjo.ui.story.viewmodel

import kr.co.devjsky.android.bobjo.di.repository.ApiRepo
import kr.co.devjsky.android.bobjo.di.repository.DataRepo
import kr.co.devjsky.android.bobjo.di.repository.UserRepo
import kr.co.devjsky.android.bobjo.ui.base.BaseViewModel

class StoryViewModel(private val apiRepo: ApiRepo, private val userRepo: UserRepo, private val dataRepo: DataRepo) : BaseViewModel() {
}