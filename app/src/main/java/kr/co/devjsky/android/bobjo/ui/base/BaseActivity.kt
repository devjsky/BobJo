package kr.co.devjsky.android.bobjo.ui.base

import android.content.Context
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

import kr.co.devjsky.android.bobjo.R
import kr.co.devjsky.android.bobjo.di.repository.ApiRepo
import kr.co.devjsky.android.bobjo.di.repository.DataRepo
import kr.co.devjsky.android.bobjo.di.repository.UserRepo
import org.koin.android.ext.android.inject

abstract class BaseActivity<B : ViewDataBinding, M : BaseViewModel> : AppCompatActivity() {
    lateinit var binding: B
    abstract val viewModel: M
    val TAG = this.javaClass.simpleName + "_LOG"
    val apiRepo : ApiRepo by inject()
    val userRepo : UserRepo by inject()
    val dataRepo : DataRepo by inject()

    var context: Context? = null

    @LayoutRes
    abstract fun getLayoutId(): Int


    open fun setTopMenu(visibleState: Boolean, title: String) {}
    open fun setBindings(){}
    open fun setObservers(){}


    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        WindowCompat.getInsetsController(window, window.decorView)?.isAppearanceLightStatusBars =
            true
        overridePendingTransition(R.anim.page_enter, R.anim.page_none)
        // 초기화된 layoutResId로 databinding 객체 생성
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        // live data를 사용하기 위해 해줘야함
        binding.lifecycleOwner = this@BaseActivity

    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.page_none, R.anim.page_exit)
    }



    // 화면 터치시 키보드 내림
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

            val focusView = currentFocus
            if (focusView != null && ev != null) {
                val rect = Rect()
                focusView.getGlobalVisibleRect(rect)
                val x = ev.x.toInt()
                val y = ev.y.toInt()

                if (!rect.contains(x, y)) {
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm?.hideSoftInputFromWindow(focusView.windowToken, 0)
                    focusView.clearFocus()
                }
            }
        return super.dispatchTouchEvent(ev)
    }







}