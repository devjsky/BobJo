package kr.co.devjsky.android.bobjo.ui.schedule.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import kr.co.devjsky.android.bobjo.R
import kr.co.devjsky.android.bobjo.api.ApiCallback
import kr.co.devjsky.android.bobjo.data.Constants.Companion.LOG_D
import kr.co.devjsky.android.bobjo.data.model.remote.IFSchedule
import kr.co.devjsky.android.bobjo.databinding.ActivityScheduleInfoBinding
import kr.co.devjsky.android.bobjo.ui.base.BaseActivity
import kr.co.devjsky.android.bobjo.ui.schedule.viewmodel.ScheduleViewModel
import org.joda.time.DateTime
import java.text.SimpleDateFormat

class ScheduleInfoActivity() : BaseActivity<ActivityScheduleInfoBinding, ScheduleViewModel>() {
    override val viewModel: ScheduleViewModel by viewModel()
    override fun getLayoutId(): Int {
        return R.layout.activity_schedule_info
    }

    var scheduleInfo: IFSchedule.ScheduleInfo? = null

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
        binding.layoutTopMenu.isVisible = visibleState
        binding.tvTopMenuTitle.text = title
    }

    override fun setBindings() {
        binding.ivDelete.setOnClickListener {
            viewModel.removeSchedule("removeSchedule", scheduleInfo?.idx!!, object : ApiCallback{
                override fun <T> result(isSuccess: Boolean, code: Int, msg: String, data: T?) {
                    if(isSuccess){
                        finish()
                    }
                }
            })
        }
    }

    override fun setObservers() {
        viewModel.testLiveData.observe(this, Observer {
            LOG_D(TAG, "info :: " + it.toString())
        })
    }
}