package kr.co.devjsky.android.bobjo.ui.main.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import kr.co.devjsky.android.bobjo.R
import kr.co.devjsky.android.bobjo.data.Constants.Companion.FRAGMENT_MAIN_COUPLE
import kr.co.devjsky.android.bobjo.data.Constants.Companion.FRAGMENT_MAIN_DASHBOARD
import kr.co.devjsky.android.bobjo.data.Constants.Companion.FRAGMENT_MAIN_MY_PAGE
import kr.co.devjsky.android.bobjo.data.Constants.Companion.FRAGMENT_MAIN_SCHEDULE
import kr.co.devjsky.android.bobjo.data.Constants.Companion.FRAGMENT_MAIN_SPENDING
import kr.co.devjsky.android.bobjo.data.Constants.Companion.LOG_D
import kr.co.devjsky.android.bobjo.databinding.ActivityMainBinding
import kr.co.devjsky.android.bobjo.ui.base.BaseActivity
import kr.co.devjsky.android.bobjo.ui.base.PopUpDialog
import kr.co.devjsky.android.bobjo.ui.main.fragment.MainCoupleFragment
import kr.co.devjsky.android.bobjo.ui.main.fragment.MainDashboardFragment
import kr.co.devjsky.android.bobjo.ui.main.fragment.MainMyPageFragment
import kr.co.devjsky.android.bobjo.ui.main.fragment.MainScheduleFragment
import kr.co.devjsky.android.bobjo.ui.main.viewmodel.MainViewModel
import kr.co.devjsky.android.bobjo.ui.schedule.activity.ScheduleAddActivity
import kr.co.devjsky.android.bobjo.ui.schedule.activity.ScheduleInfoActivity
import kr.co.devjsky.android.bobjo.ui.schedule.dialog.ScheduleSubMenuDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.StringBuilder

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val viewModel: MainViewModel by viewModel()



    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }


    private val bottomMenuList:ArrayList<ImageView> = arrayListOf()
    var nowFragment:Fragment? = null

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        setBottomMenu()
        setBottomMenuMyPageUserProfileImage()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onBackPressed() {
        binding.layoutSubMenuTopMenu.visibility = View.GONE
        binding.layoutSubMenuBottomMenu.visibility = View.GONE
        if(nowFragment != null){
            if(nowFragment?.tag == FRAGMENT_MAIN_SCHEDULE){
                (nowFragment as MainScheduleFragment).viewModel.multiCheckLiveData.value = false
            }
        }
        super.onBackPressed()

    }

    val backKeyCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // 뒤로 버튼 이벤트 처리
            Log.e(TAG, "뒤로가기 클릭")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        viewModel.mainActivity = this
        onBackPressedDispatcher.addCallback(this, backKeyCallback)

        setBindings()
        setObservers()

    }



    override fun setBindings() {

        binding.btnSubMenuBottomCancel.setOnClickListener {
            binding.layoutSubMenuTopMenu.visibility = View.GONE
            binding.layoutSubMenuBottomMenu.visibility = View.GONE
            if(nowFragment != null){
                if(nowFragment?.tag == FRAGMENT_MAIN_SCHEDULE){
                    (nowFragment as MainScheduleFragment).viewModel.multiCheckLiveData.value = false
                    dataRepo.schedule_calendar_multi_check_list = null
                    dataRepo.schedule_calendar_multi_check_list = ArrayList()
                }
            }

        }
        binding.btnSubMenuBottomConfirm.setOnClickListener {
            binding.layoutSubMenuTopMenu.visibility = View.GONE
            binding.layoutSubMenuBottomMenu.visibility = View.GONE
            if(nowFragment != null){
                if(nowFragment?.tag == FRAGMENT_MAIN_SCHEDULE){

                    if(dataRepo.schedule_calendar_multi_check_list != null && dataRepo.schedule_calendar_multi_check_list!!.size>0){
                        val sb = StringBuilder()
                        sb.append(dataRepo.schedule_calendar_multi_check_list!![0])
                        for (i in 1 until dataRepo.schedule_calendar_multi_check_list!!.size){
                            sb.append(",${dataRepo.schedule_calendar_multi_check_list!![i]}")
                        }
                        val dates = sb.toString()
                        (nowFragment as MainScheduleFragment).viewModel.addScheduleMulti(
                            "addScheduleMulti"
                            ,dates
                            ,"DAYOFF"
                            ,"지호 휴무"
                            ,"지호 쉬는날 입니당"
                            , "Y"
                            , "N"
                            ,"N"
                            ,"N"
                            ,"Y")
                    }



                    (nowFragment as MainScheduleFragment).viewModel.multiCheckLiveData.value = false
                    dataRepo.schedule_calendar_multi_check_list = null
                    dataRepo.schedule_calendar_multi_check_list = ArrayList()

                }
            }

        }
    }

    override fun setObservers() {

    }
    override fun setTopMenu(visibleState: Boolean, title: String) {
        binding.layoutTopMenu.layoutBody.isVisible = visibleState
        binding.layoutTopMenu.tvTopMenuTitle.text = title
        binding.layoutSubMenuTopMenu.visibility = View.GONE
        binding.layoutSubMenuBottomMenu.visibility = View.GONE
        if(nowFragment != null){
            if(nowFragment?.tag == FRAGMENT_MAIN_SCHEDULE){
                binding.layoutTopMenu.ivSubMenu.visibility = View.VISIBLE
            }else{
                binding.layoutTopMenu.ivSubMenu.visibility = View.INVISIBLE
            }
            binding.layoutTopMenu.ivSubMenu.setOnClickListener {
                if(nowFragment?.tag == FRAGMENT_MAIN_SCHEDULE){
                    val fragmentMainScheduleSubMenuDialog = ScheduleSubMenuDialog()
                    fragmentMainScheduleSubMenuDialog.setOnBtnClickListener(object : ScheduleSubMenuDialog.OnBtnClickEventListener{
                        override fun addDayOffBtnClick() {
                            (nowFragment as MainScheduleFragment).viewModel.multiCheckLiveData.value = true
                            binding.layoutSubMenuTopMenu.visibility = View.VISIBLE
                            binding.layoutSubMenuBottomMenu.visibility = View.VISIBLE
                            dataRepo.schedule_calendar_multi_check_list = null
                            dataRepo.schedule_calendar_multi_check_list = ArrayList()
                        }
                    })
                    fragmentMainScheduleSubMenuDialog.show(supportFragmentManager, "ScheduleSubMenuDialog")


                }

            }
        }


    }

    fun setBottomMenu(){


        viewModel.bottomMenuLiveData.observe(this, Observer {
            val fragmentTag = it
            var fragment: Fragment? = null
            when(it){
                FRAGMENT_MAIN_DASHBOARD -> {
                    fragment = MainDashboardFragment()
                    nowFragment = fragment
                    setFragment(fragment, fragmentTag)
                    setBottomMenuTint(binding.ivBottomMenu01)
                }
                FRAGMENT_MAIN_COUPLE -> {
                    fragment = MainCoupleFragment()
                    nowFragment = fragment
                    setFragment(fragment, fragmentTag)
                    runOnUiThread {
                        bottomMenuList[1].setImageResource(dataRepo.getBottomMenuCoupleIconImage())
                    }

                    setBottomMenuTint(binding.ivBottomMenu02)
                }
                FRAGMENT_MAIN_SCHEDULE -> {
                    fragment = MainScheduleFragment()
                    nowFragment = fragment
                    setFragment(fragment, fragmentTag)
                    setBottomMenuTint(binding.ivBottomMenu03)
                    setTopMenu(true, "일정")
                }
                FRAGMENT_MAIN_SPENDING -> {
                    fragment = MainCoupleFragment()
                    nowFragment = fragment
                    setFragment(fragment, fragmentTag)
                    setBottomMenuTint(binding.ivBottomMenu04)

                }
                FRAGMENT_MAIN_MY_PAGE -> {
                    fragment = MainMyPageFragment()
                    nowFragment = fragment
                    setFragment(fragment, fragmentTag)
                    setBottomMenuTint(binding.ivBottomMenu05)

                }
            }

        })
        viewModel.testDataLiveData.observe(this, Observer {
            Log.e(TAG, it.toString())
        })

        binding.ivBottomMenu01.setOnClickListener { viewModel.setBottomMenu(FRAGMENT_MAIN_DASHBOARD) }
        binding.ivBottomMenu02.setOnClickListener { viewModel.setBottomMenu(FRAGMENT_MAIN_COUPLE) }
        binding.ivBottomMenu03.setOnClickListener { viewModel.setBottomMenu(FRAGMENT_MAIN_SCHEDULE) }
        binding.ivBottomMenu04.setOnClickListener { viewModel.setBottomMenu(FRAGMENT_MAIN_SPENDING) }
        binding.ivBottomMenu05.setOnClickListener { viewModel.setBottomMenu(FRAGMENT_MAIN_MY_PAGE) }

        bottomMenuList.add(binding.ivBottomMenu01)
        bottomMenuList.add(binding.ivBottomMenu02)
        bottomMenuList.add(binding.ivBottomMenu03)
        bottomMenuList.add(binding.ivBottomMenu04)
        bottomMenuList.add(binding.ivBottomMenu05)
        runOnUiThread {
            bottomMenuList[1].setImageResource(dataRepo.getBottomMenuCoupleIconImage())
        }

    }
    fun setBottomMenuTint(view:ImageView){
        for(menu in bottomMenuList){
            if(view.id == menu.id){
                if(menu.id != bottomMenuList[1].id && menu.id != bottomMenuList.last().id){
                    menu.setColorFilter(resources.getColor(R.color.bottom_menu_selected_color, null))
                }
            }else{
                if(menu.id != bottomMenuList[1].id && menu.id != bottomMenuList.last().id){
                    menu.setColorFilter(resources.getColor(R.color.bottom_menu_unselected_color, null))
                }
            }
        }
    }
    fun setFragment(fragment: Fragment, tag: String){

        val fm: FragmentManager = supportFragmentManager
        val ft: FragmentTransaction = fm.beginTransaction()
        ft.replace(R.id.layout_body, fragment, tag)
        ft.addToBackStack(null)
        ft.commit()
    }



    fun setBottomMenuMyPageUserProfileImage(){
        LOG_D(TAG, "MAIN :: setBottomMenuMyPageUserProfileImage()")
        Glide.with(this).load("https://soundleader.co.kr/webrtc/API2/data/profile_img_20230324100603000000_94.jpg").error(R.drawable.ico_user_01).override(256,256).into(bottomMenuList.last())

    }


}