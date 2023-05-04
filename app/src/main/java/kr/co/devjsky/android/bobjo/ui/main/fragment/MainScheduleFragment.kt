package kr.co.devjsky.android.bobjo.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnScrollChangeListener
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnScrollChangedListener
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.viewpager2.widget.ViewPager2
import kr.co.devjsky.android.bobjo.R
import kr.co.devjsky.android.bobjo.data.Constants.Companion.LOG_D
import kr.co.devjsky.android.bobjo.databinding.FragmentMainScheduleBinding
import kr.co.devjsky.android.bobjo.ui.base.BaseFragment
import kr.co.devjsky.android.bobjo.ui.main.viewmodel.MainViewModel
import kr.co.devjsky.android.bobjo.ui.schedule.adapter.SchduleCalendarAdapter
import kr.co.devjsky.android.bobjo.ui.schedule.viewmodel.ScheduleViewModel
import org.joda.time.DateTime
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainScheduleFragment : BaseFragment<FragmentMainScheduleBinding, ScheduleViewModel>() {
    override val viewModel: ScheduleViewModel by sharedViewModel()

    override fun getLayoutId(): Int {
        return R.layout.fragment_main_schedule
    }
    private lateinit var schduleCalendarAdapter: SchduleCalendarAdapter

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        setBindings()
        setObservers()
        initCalendar()


    }

    override fun setBindings() {

    }

    override fun setObservers() {




    }
    fun initCalendar() {
        schduleCalendarAdapter = SchduleCalendarAdapter(context)

        binding.vpCalendar.adapter = schduleCalendarAdapter
        binding.vpCalendar.setCurrentItem(SchduleCalendarAdapter.START_POSITION, false)
        binding.vpCalendar.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                LOG_D(TAG, "onPageSelected")
                viewModel.eventListClearLiveData.value = true

                viewModel.getSchedule("getMonthSchedule", DateTime(schduleCalendarAdapter.getItemId(position)).toString("yyyy"), DateTime(schduleCalendarAdapter.getItemId(position)).toString("MM"),"")
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })
    }


    override fun setLoading(value: Boolean){
        binding.layoutLoading.isVisible = value
    }
}