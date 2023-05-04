package kr.co.devjsky.android.bobjo.ui.base

import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    val TAG = this.javaClass.simpleName + "_LOG"
}