package kr.co.devjsky.android.bobjo.ui.schedule.activity

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import kr.co.devjsky.android.bobjo.R
import kr.co.devjsky.android.bobjo.data.Constants
import kr.co.devjsky.android.bobjo.data.Constants.Companion.LOG_D
import kr.co.devjsky.android.bobjo.data.model.remote.IFSchedule
import kr.co.devjsky.android.bobjo.data.schedule.CalendarGroup.Companion.ANNIVERSARY
import kr.co.devjsky.android.bobjo.data.schedule.CalendarGroup.Companion.DAYOFF
import kr.co.devjsky.android.bobjo.data.schedule.CalendarGroup.Companion.MEMO
import kr.co.devjsky.android.bobjo.databinding.ActivityScheduleAddBinding
import kr.co.devjsky.android.bobjo.ui.base.BaseActivity
import kr.co.devjsky.android.bobjo.ui.schedule.viewmodel.ScheduleViewModel
import kr.co.devjsky.android.bobjo.utils.calendar.CalendarUtils
import java.text.SimpleDateFormat

class ScheduleAddActivity() : BaseActivity<ActivityScheduleAddBinding, ScheduleViewModel>() {
    override val viewModel: ScheduleViewModel by viewModel()
    override fun getLayoutId(): Int {
        return R.layout.activity_schedule_add
    }
    var scheduleInfo: IFSchedule.ScheduleInfo? = null
    val radioCheckedDrawable = R.drawable.bg_radio_checked_01
    val radioUnCheckedDrawable = R.drawable.bg_radio_unchecked_01


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        scheduleInfo = dataRepo.selected_schedule_info_data

        setBindings()
        setObservers()
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val date = sdf.parse(scheduleInfo?.startDate)
        val scheduleDate = sdf.format(date)
        setTopMenu(true, scheduleDate)
    }

    override fun setTopMenu(visibleState: Boolean, title: String) {
        binding.layoutTopMenu.layoutBody.isVisible = visibleState
        binding.layoutTopMenu.tvTopMenuTitle.isVisible = true
        binding.layoutTopMenu.ivBack.isVisible = true
        binding.layoutTopMenu.ivSubMenu.isVisible = false
        binding.layoutTopMenu.ivDelete.isVisible = false

        binding.layoutTopMenu.tvTopMenuTitle.text = title
    }

    override fun setBindings() {
        setLayoutCategoryGroupBindings()
        setLayoutEventColorBindings()
    }

    override fun setObservers() {
        viewModel.alldayCheckLiveData.observe(this, Observer {
            binding.layoutTimeSelect.isVisible = !it
        })
        viewModel.categoryGroupLiveData.observe(this, Observer {
            if(it != ""){
                runOnUiThread {
                    if(ivCategoryGroupArr != null && ivCategoryGroupArr!!.isNotEmpty()) {
                        for(ivCategoryGroup in ivCategoryGroupArr!!){ ivCategoryGroup.setImageResource(radioUnCheckedDrawable) }
                    }

                    when(it){
                        MEMO -> { binding.ivCategoryGroupMemo.setImageResource(radioCheckedDrawable) }
                        DAYOFF -> { binding.ivCategoryGroupDayoff.setImageResource(radioCheckedDrawable) }
                        ANNIVERSARY -> { binding.ivCategoryGroupAnniversary.setImageResource(radioCheckedDrawable) }
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
    }

    var ivCategoryGroupArr:Array<ImageView>? = null
    fun setLayoutCategoryGroupBindings(){
        ivCategoryGroupArr = arrayOf(
            binding.ivCategoryGroupMemo,binding.ivCategoryGroupDayoff,binding.ivCategoryGroupAnniversary
        )

        binding.layoutCategoryGroupMemo.setOnClickListener { viewModel.categoryGroupLiveData.value = "MEMO" }
        binding.layoutCategoryGroupDayoff.setOnClickListener { viewModel.categoryGroupLiveData.value = "DAYOFF" }
        binding.layoutCategoryGroupAnniversary.setOnClickListener { viewModel.categoryGroupLiveData.value = "ANNIVERSARY" }
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