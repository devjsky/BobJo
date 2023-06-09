package kr.co.devjsky.android.bobjo.ui.schedule.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.res.ColorStateList
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import kr.co.devjsky.android.bobjo.R
import kr.co.devjsky.android.bobjo.data.Constants.Companion.LOG_D
import kr.co.devjsky.android.bobjo.data.model.remote.IFSchedule
import kr.co.devjsky.android.bobjo.data.schedule.CalendarGroup.Companion.ANNIVERSARY
import kr.co.devjsky.android.bobjo.data.schedule.CalendarGroup.Companion.DAYOFF
import kr.co.devjsky.android.bobjo.data.schedule.CalendarGroup.Companion.MEMO
import kr.co.devjsky.android.bobjo.data.schedule.ScheduleState.Companion.FRIENDS
import kr.co.devjsky.android.bobjo.data.schedule.ScheduleState.Companion.ME
import kr.co.devjsky.android.bobjo.databinding.ActivityScheduleAddBinding
import kr.co.devjsky.android.bobjo.ui.base.BaseActivity
import kr.co.devjsky.android.bobjo.ui.base.PopUpDialog
import kr.co.devjsky.android.bobjo.ui.schedule.viewmodel.ScheduleViewModel
import kr.co.devjsky.android.bobjo.utils.DateUtils
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.util.Date

class ScheduleAddActivity() : BaseActivity<ActivityScheduleAddBinding, ScheduleViewModel>() {
    override val viewModel: ScheduleViewModel by viewModel()
    override fun getLayoutId(): Int {
        return R.layout.activity_schedule_add
    }
    var scheduleInfo: IFSchedule.ScheduleInfo? = null
    val radioCheckedDrawable = R.drawable.bg_schedule_add_state_02
    val radioUnCheckedDrawable = R.drawable.bg_schedule_add_state_01
    val radioCheckedTextColor = R.color.white
    val radioUnCheckedTextColor = R.color.black
    val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        scheduleInfo = dataRepo.selected_schedule_info_data

        setBindings()
        setObservers()
        setDefaultUI()

    }
    fun setDefaultUI(){
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val date = sdf.parse(scheduleInfo?.startDate)
        val scheduleDate = sdf.format(date)
        calendar.time = date
        binding.tvStartDate.text = SimpleDateFormat("M월 d일 (E)").format(date).toString()
        binding.tvEndDate.text = SimpleDateFormat("M월 d일 (E)").format(date).toString()
        viewModel.startDateLiveData.value = scheduleDate
        viewModel.endDateLiveData.value = scheduleDate
        binding.tvStartDateTime.text = DateUtils.getTimeString("2014-09-18 08:00:00", "a HH:mm")
        binding.tvEndDateTime.text = DateUtils.getTimeString("2014-09-18 09:00:00", "a HH:mm")
        viewModel.startTimeLiveData.value = "08:00:00"
        viewModel.endTimeLiveData.value = "09:00:00"

        setTopMenu(true, DateUtils.getDateString(scheduleInfo?.startDate.toString(), "yyyy-MM-dd (E)"))
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
        binding.btnCancel.setOnClickListener {
            finish()
        }
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
        binding.btnAddSchedule.setOnClickListener {
            viewModel.addSchedule()
        }
    }

    override fun setObservers() {
        viewModel.scheduleAddFinLiveData.observe(this, Observer {
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
                            (layoutCategoryGroup.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(this, R.color.black))
                        }
                    }

                    when(it){
                        MEMO -> {
                            binding.layoutCategoryGroupMemo.setBackgroundResource(radioCheckedDrawable)
                            (binding.layoutCategoryGroupMemo.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(this, R.color.white))
                        }
                        DAYOFF -> {
                            binding.layoutCategoryGroupDayoff.setBackgroundResource(radioCheckedDrawable)
                            (binding.layoutCategoryGroupDayoff.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(this, R.color.white))
                        }
                        ANNIVERSARY -> {
                            binding.layoutCategoryGroupAnniversary.setBackgroundResource(radioCheckedDrawable)
                            (binding.layoutCategoryGroupAnniversary.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(this, R.color.white))
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
                        ME -> {
                            binding.layoutStateMe.setBackgroundResource(radioCheckedDrawable)
                            (binding.layoutStateMe.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(this, R.color.white))
                        }
                        FRIENDS -> {
                            binding.layoutStateFriends.setBackgroundResource(radioCheckedDrawable)
                            (binding.layoutStateFriends.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(this, R.color.white))
                        }
                    }
                }
            }
        })
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