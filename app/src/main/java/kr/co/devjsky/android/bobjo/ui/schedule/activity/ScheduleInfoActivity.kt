package kr.co.devjsky.android.bobjo.ui.schedule.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.res.ColorStateList
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import kr.co.devjsky.android.bobjo.R
import kr.co.devjsky.android.bobjo.api.ApiCallback
import kr.co.devjsky.android.bobjo.data.Constants.Companion.LOG_D
import kr.co.devjsky.android.bobjo.data.model.remote.IFSchedule
import kr.co.devjsky.android.bobjo.data.schedule.CalendarGroup
import kr.co.devjsky.android.bobjo.data.schedule.ScheduleState
import kr.co.devjsky.android.bobjo.databinding.ActivityScheduleInfoBinding
import kr.co.devjsky.android.bobjo.ui.base.BaseActivity
import kr.co.devjsky.android.bobjo.ui.base.PopUpDialog
import kr.co.devjsky.android.bobjo.ui.schedule.viewmodel.ScheduleViewModel
import kr.co.devjsky.android.bobjo.utils.DateUtils
import org.joda.time.DateTime
import java.text.SimpleDateFormat

class ScheduleInfoActivity() : BaseActivity<ActivityScheduleInfoBinding, ScheduleViewModel>() {
    override val viewModel: ScheduleViewModel by viewModel()
    override fun getLayoutId(): Int {
        return R.layout.activity_schedule_info
    }

    var scheduleInfo: IFSchedule.ScheduleInfo? = null
    val radioCheckedDrawable = R.drawable.bg_schedule_add_state_02
    val radioUnCheckedDrawable = R.drawable.bg_schedule_add_state_01
    val calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        scheduleInfo = dataRepo.selected_schedule_info_data

        setBindings()
        setObservers()
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val date = sdf.parse(scheduleInfo?.startDate)
        val scheduleDate = sdf.format(date)
        setTopMenu(true, DateUtils.getDateString(scheduleInfo?.startDate.toString(), "yyyy-MM-dd (E)"))

        setInfo()
    }

    override fun setTopMenu(visibleState: Boolean, title: String) {
        binding.layoutTopMenu.layoutBody.isVisible = visibleState
        binding.layoutTopMenu.tvTopMenuTitle.isVisible = true
        binding.layoutTopMenu.ivBack.isVisible = true
        binding.layoutTopMenu.ivSubMenu.isVisible = false
        binding.layoutTopMenu.ivDelete.isVisible = false

        binding.layoutTopMenu.tvTopMenuTitle.text = title
        binding.layoutTopMenu.ivBack.setOnClickListener {
            finish()
        }

    }

    override fun setBindings() {
        setLayoutCategoryGroupBindings()
        setLayoutEventColorBindings()
        setLayoutStateBindings()
        binding.tvStartDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,R.style.SpinnerDatePickerDialogStyle,
                { datePicker, _year, _month, _day ->
                    val tempMonth:String = if((_month + 1)/10 == 0){
                        ("0"+(_month + 1))
                    } else {
                        (_month + 1).toString()
                    }
                    val dateTime = "$_year-$tempMonth-$_day"
                    binding.tvStartDate.text = DateUtils.getDateString("$dateTime 00:00:00", "M월 d일 (E)")

                    viewModel.startDateLiveData.value = dateTime
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "선택", datePickerDialog)
            datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "닫기", datePickerDialog)
            datePickerDialog.show()
        }
        binding.tvEndDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,R.style.SpinnerDatePickerDialogStyle,
                { datePicker, _year, _month, _day ->
                    val tempMonth:String = if((_month + 1)/10 == 0){
                        ("0"+(_month + 1))
                    } else {
                        (_month + 1).toString()
                    }
                    val dateTime = "$_year-$tempMonth-$_day"
                    binding.tvEndDate.text = DateUtils.getDateString("$dateTime 00:00:00", "M월 d일 (E)")
                    viewModel.endDateLiveData.value = dateTime
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "선택", datePickerDialog)
            datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "닫기", datePickerDialog)
            datePickerDialog.show()



        }
        binding.tvStartDateTime.setOnClickListener {
            val timePickerDialog = TimePickerDialog(
                this,R.style.SpinnerTimePickerDialogStyle, {timePicker, _hourOfDay, _minute ->
                    val dateTime = "$_hourOfDay:$_minute:00"
                    binding.tvStartDateTime.text = DateUtils.getTimeString("2014-09-18 $dateTime", "a HH:mm")
                    viewModel.startTimeLiveData.value = dateTime
                },8,0, true)


            timePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "선택", timePickerDialog)
            timePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "닫기", timePickerDialog)
            timePickerDialog.show()
        }
        binding.tvEndDateTime.setOnClickListener {
            val timePickerDialog = TimePickerDialog(
                this,R.style.SpinnerTimePickerDialogStyle, {timePicker, _hourOfDay, _minute ->
                    val dateTime = "$_hourOfDay:$_minute:00"
                    binding.tvEndDateTime.text = DateUtils.getTimeString("2014-09-18 $dateTime", "a HH:mm")
                    viewModel.endTimeLiveData.value = dateTime
                },8,0, true)


            timePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "선택", timePickerDialog)
            timePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "닫기", timePickerDialog)
            timePickerDialog.show()
        }
        binding.btnDelete.setOnClickListener {
            viewModel.removeSchedule("removeSchedule", scheduleInfo?.idx!!, object : ApiCallback{
                override fun <T> result(isSuccess: Boolean, code: Int, msg: String, data: T?) {
                    if(isSuccess){
                        finish()
                    }
                }
            })
        }
        binding.btnModify.setOnClickListener {
            viewModel.modifySchedule()
        }
    }

    override fun setObservers() {
        viewModel.scheduleModifyFinLiveData.observe(this, Observer {
            if(it){
                finish()
            }
        })
        viewModel.checkValidAlertLiveData.observe(this, Observer {
            if(it != null && it != ""){
                val checkValidDialog = PopUpDialog()
                checkValidDialog.setData(true, "알림", it, "","","")
                checkValidDialog.show(supportFragmentManager, "checkValidDialog")
            }
        })
        viewModel.allDayCheckLiveData.observe(this, Observer {
            binding.layoutTimeSelect.isVisible = !it
        })
        viewModel.categoryGroupLiveData.observe(this, Observer {
            if(it != ""){
                runOnUiThread {
                    if(layoutCategoryGroupArr != null && layoutCategoryGroupArr!!.isNotEmpty()) {
                        for(layoutCategoryGroup in layoutCategoryGroupArr!!){
                            layoutCategoryGroup.setBackgroundResource(radioUnCheckedDrawable)
                            (layoutCategoryGroup.getChildAt(0) as TextView).setTextColor(
                                ContextCompat.getColor(this, R.color.black))
                        }
                    }

                    when(it){
                        CalendarGroup.MEMO -> {
                            binding.layoutCategoryGroupMemo.setBackgroundResource(radioCheckedDrawable)
                            (binding.layoutCategoryGroupMemo.getChildAt(0) as TextView).setTextColor(
                                ContextCompat.getColor(this, R.color.white))
                        }
                        CalendarGroup.DAYOFF -> {
                            binding.layoutCategoryGroupDayoff.setBackgroundResource(radioCheckedDrawable)
                            (binding.layoutCategoryGroupDayoff.getChildAt(0) as TextView).setTextColor(
                                ContextCompat.getColor(this, R.color.white))
                        }
                        CalendarGroup.ANNIVERSARY -> {
                            binding.layoutCategoryGroupAnniversary.setBackgroundResource(radioCheckedDrawable)
                            (binding.layoutCategoryGroupAnniversary.getChildAt(0) as TextView).setTextColor(
                                ContextCompat.getColor(this, R.color.white))
                        }
                    }
                }
            }
        })
        viewModel.tagColorLiveData.observe(this, Observer {
            runOnUiThread {
                if(ivEventColorArr != null && ivEventColorArr!!.isNotEmpty()) {
                    for(ivEventColor in ivEventColorArr!!){
                        ivEventColor.imageTintList = ColorStateList.valueOf(
                            ContextCompat.getColor(this, R.color.transparent))
                    }
                    ivEventColorArr!![it].imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.main_color))
                }





            }
        })
        viewModel.stateLiveData.observe(this, Observer {
            if(it != ""){
                runOnUiThread {
                    if(layoutStateArr != null && layoutStateArr!!.isNotEmpty()) {
                        for(layoutState in layoutStateArr!!){
                            layoutState.setBackgroundResource(radioUnCheckedDrawable)
                            (layoutState.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(this, R.color.black))
                        }
                    }

                    when(it){
                        ScheduleState.ME -> {
                            binding.layoutStateMe.setBackgroundResource(radioCheckedDrawable)
                            (binding.layoutStateMe.getChildAt(0) as TextView).setTextColor(
                                ContextCompat.getColor(this, R.color.white))
                        }
                        ScheduleState.FRIENDS -> {
                            binding.layoutStateFriends.setBackgroundResource(radioCheckedDrawable)
                            (binding.layoutStateFriends.getChildAt(0) as TextView).setTextColor(
                                ContextCompat.getColor(this, R.color.white))
                        }
                    }
                }
            }
        })
    }

    fun setInfo(){
        if(scheduleInfo != null){
            if(scheduleInfo?.category_group != null){
                viewModel.categoryGroupLiveData.value = scheduleInfo?.category_group
            }
            if(scheduleInfo?.tag_color != null){
                viewModel.tagColorLiveData.value = scheduleInfo?.tag_color
            }
            if(scheduleInfo?.title != null){
                viewModel.titleLiveData.value = scheduleInfo?.title
            }
            if(scheduleInfo?.content != null){
                viewModel.contentLiveData.value = scheduleInfo?.content
            }

            if(scheduleInfo?.startDate != null){
                val date = scheduleInfo?.startDate!!.split(" ")
                viewModel.startDateLiveData.value = date[0]
                viewModel.startTimeLiveData.value = date[1]
                binding.tvStartDate.text = DateUtils.getDateString("${date[0]} 00:00:00", "M월 d일 (E)")
                binding.tvStartDateTime.text = DateUtils.getTimeString("2014-09-18 ${date[1]}", "a HH:mm")
            }
            if(scheduleInfo?.endDate != null){
                val date = scheduleInfo?.endDate!!.split(" ")
                viewModel.endDateLiveData.value = date[0]
                viewModel.endTimeLiveData.value = date[1]
                binding.tvEndDate.text = DateUtils.getDateString("${date[0]} 00:00:00", "M월 d일 (E)")
                binding.tvEndDateTime.text = DateUtils.getTimeString("2014-09-18 ${date[1]}", "a HH:mm")
            }
            if(scheduleInfo?.state != null){
                viewModel.stateLiveData.value = scheduleInfo?.state
            }
            if(scheduleInfo?.allday != null){
                viewModel.allDayLiveData.value = scheduleInfo?.allday
                when(scheduleInfo?.allday){
                    "Y" -> {
                        binding.swAllDay.isChecked = true
                        viewModel.allDayCheckLiveData.value = true
                        viewModel.startTimeLiveData.value = "00:00:00"
                        viewModel.endDateLiveData.value = "00:00:00"
                    }
                    "N" -> {
                        binding.swAllDay.isChecked = false
                        viewModel.allDayCheckLiveData.value = false

                    }
                }

            }
        }
    }

    var layoutCategoryGroupArr:Array<LinearLayout>? = null
    fun setLayoutCategoryGroupBindings(){
        layoutCategoryGroupArr = arrayOf(
            binding.layoutCategoryGroupMemo,binding.layoutCategoryGroupDayoff,binding.layoutCategoryGroupAnniversary
        )

        binding.layoutCategoryGroupMemo.setOnClickListener { viewModel.categoryGroupLiveData.value = "MEMO" }
        binding.layoutCategoryGroupDayoff.setOnClickListener { viewModel.categoryGroupLiveData.value = "DAYOFF" }
        binding.layoutCategoryGroupAnniversary.setOnClickListener { viewModel.categoryGroupLiveData.value = "ANNIVERSARY" }
    }
    var layoutStateArr:Array<LinearLayout>? = null
    fun setLayoutStateBindings(){
        layoutStateArr = arrayOf(
            binding.layoutStateMe,binding.layoutStateFriends
        )

        binding.layoutStateMe.setOnClickListener { viewModel.stateLiveData.value = "ME" }
        binding.layoutStateFriends.setOnClickListener { viewModel.stateLiveData.value = "FRIENDS" }
    }
    var ivEventColorArr:Array<ImageView>? = null
    fun setLayoutEventColorBindings(){
        ivEventColorArr = arrayOf(
            binding.ivEventColor01,binding.ivEventColor02,binding.ivEventColor03,binding.ivEventColor04,binding.ivEventColor05,
            binding.ivEventColor06,binding.ivEventColor07,binding.ivEventColor08,binding.ivEventColor09,binding.ivEventColor10
        )

        binding.layoutEventColor01.setOnClickListener { viewModel.tagColorLiveData.value = 0 }
        binding.layoutEventColor02.setOnClickListener { viewModel.tagColorLiveData.value = 1 }
        binding.layoutEventColor03.setOnClickListener { viewModel.tagColorLiveData.value = 2 }
        binding.layoutEventColor04.setOnClickListener { viewModel.tagColorLiveData.value = 3 }
        binding.layoutEventColor05.setOnClickListener { viewModel.tagColorLiveData.value = 4 }
        binding.layoutEventColor06.setOnClickListener { viewModel.tagColorLiveData.value = 5 }
        binding.layoutEventColor07.setOnClickListener { viewModel.tagColorLiveData.value = 6 }
        binding.layoutEventColor08.setOnClickListener { viewModel.tagColorLiveData.value = 7 }
        binding.layoutEventColor09.setOnClickListener { viewModel.tagColorLiveData.value = 8 }
        binding.layoutEventColor10.setOnClickListener { viewModel.tagColorLiveData.value = 9 }
    }
}