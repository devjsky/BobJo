package kr.co.devjsky.android.bobjo.ui.schedule.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import kr.co.devjsky.android.bobjo.R
import kr.co.devjsky.android.bobjo.data.Constants
import kr.co.devjsky.android.bobjo.data.Constants.Companion.LOG_D
import kr.co.devjsky.android.bobjo.data.model.remote.IFSchedule
import kr.co.devjsky.android.bobjo.databinding.ActivityScheduleAddBinding
import kr.co.devjsky.android.bobjo.ui.base.BaseActivity
import kr.co.devjsky.android.bobjo.ui.schedule.viewmodel.ScheduleViewModel
import java.text.SimpleDateFormat

class ScheduleAddActivity() : BaseActivity<ActivityScheduleAddBinding, ScheduleViewModel>() {
    override val viewModel: ScheduleViewModel by viewModel()
    override fun getLayoutId(): Int {
        return R.layout.activity_schedule_add
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
//        binding.layoutTopMenu.layoutBody.isVisible = visibleState
//        binding.layoutTopMenu.tvTopMenuTitle.text = title
    }

    override fun setBindings() {

    }

    override fun setObservers() {
        viewModel.testLiveData.observe(this, Observer {
            LOG_D(TAG, "add :: " + it.toString())
        })
    }
}