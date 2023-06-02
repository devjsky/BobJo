package kr.co.devjsky.android.bobjo.ui.splash.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.co.devjsky.android.bobjo.R
import kr.co.devjsky.android.bobjo.data.Constants.Companion.LOG_D
import kr.co.devjsky.android.bobjo.databinding.ActivitySplashBinding
import kr.co.devjsky.android.bobjo.ui.base.BaseActivity
import kr.co.devjsky.android.bobjo.ui.main.activity.MainActivity
import kr.co.devjsky.android.bobjo.ui.splash.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {
    override val viewModel: SplashViewModel by viewModel()

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun onResume() {
        super.onResume()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBindings()
        setObservers()
        userRepo.userInfo?.userToken = "abcd"
        LOG_D(TAG, "userRepo.userInfo?.userToken : ${userRepo.userInfo?.userToken}")
        moveMainActivity()



    }

    override fun setBindings() {

    }

    override fun setObservers() {
    }

    fun moveMainActivity() {

        CoroutineScope(Dispatchers.Main).launch {

            delay(2000)

            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)


        }

    }

}