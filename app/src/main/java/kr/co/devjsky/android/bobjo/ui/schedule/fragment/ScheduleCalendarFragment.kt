package kr.co.devjsky.android.bobjo.ui.schedule.fragment

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import kr.co.devjsky.android.bobjo.R
import kr.co.devjsky.android.bobjo.data.Constants.Companion.LOG_D
import kr.co.devjsky.android.bobjo.data.model.remote.IFSchedule
import kr.co.devjsky.android.bobjo.data.schedule.CalendarGroup
import kr.co.devjsky.android.bobjo.databinding.FragmentScheduleCalendarBinding
import kr.co.devjsky.android.bobjo.ui.base.BaseFragment
import kr.co.devjsky.android.bobjo.ui.schedule.activity.ScheduleAddActivity
import kr.co.devjsky.android.bobjo.ui.schedule.activity.ScheduleInfoActivity
import kr.co.devjsky.android.bobjo.ui.schedule.viewmodel.ScheduleViewModel
import kr.co.devjsky.android.bobjo.utils.calendar.CalendarUtils
import kr.co.devjsky.android.bobjo.utils.calendar.CalendarUtils.Companion.getMonthList
import org.joda.time.DateTime
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.text.SimpleDateFormat

class ScheduleCalendarFragment : BaseFragment<FragmentScheduleCalendarBinding, ScheduleViewModel>() {
    override val viewModel: ScheduleViewModel by sharedViewModel()

    override fun getLayoutId(): Int {
        return R.layout.fragment_schedule_calendar
    }

    private var millis: Long = 0L
    var layoutDayList:Array<Array<RelativeLayout>>? = null
    var tvDayList:Array<Array<TextView>>? = null
    var layoutEventList:Array<Array<LinearLayout>>? = null
    var layoutMultiCheckList:Array<Array<LinearLayout>>? = null

    var layoutDayListSetFinList = ArrayList<RelativeLayout>()
    var layoutEventSetFinList = ArrayList<LinearLayout>()
    var layoutMultiCheckSetFinList = ArrayList<LinearLayout>()

    var multiCheckMode = false

    var selectedDate:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            millis = it.getLong(MILLIS)
        }
        LOG_D(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        LOG_D(TAG, "onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LOG_D(TAG, "onViewCreated")
        binding.viewModel = viewModel
        setBindings()
        setObservers()
        viewModel.getSchedule("getMonthSchedule", DateTime(millis).toString("yyyy"), DateTime(millis).toString("MM"),"")

    }

    override fun setBindings() {
        binding.tvMillis.text = DateTime(millis).toString("yyyy-MM")

        setLayoutDayListBindings()

    }

    override fun setObservers() {
        viewModel.pageClearLiveData.observe(this, Observer {
            if(it){
                setPageClear()
            }
        })
        viewModel.scheduleLiveData.observe(this, Observer {
            for(layoutEvent in layoutEventSetFinList){
                layoutEvent.removeAllViews()
            }
            if(it != null){

                val scheduleInfo = it.schedule_info
                val selectedMonth = DateTime(millis).toString("MM")
                if(scheduleInfo != null && scheduleInfo.size > 0){

                    for(scheduleData in scheduleInfo){
                        val sdf = SimpleDateFormat("yyyy-MM-dd")
                        val date = sdf.parse(scheduleData.startDate)
                        val scheduleDate = sdf.format(date)
                        val layoutEvent = layoutEventSetFinList.find { x->(x.tag as Array<String>)[2] == scheduleDate.toString() }
                        if(layoutEvent != null){

                            val itemView = LayoutInflater.from(context).inflate(R.layout.view_calendar_event, null)
                            val recordTextView = itemView.findViewById<TextView>(R.id.tv_title)
                            recordTextView.text = scheduleData.title
                            when(scheduleData.category_group){
                                CalendarGroup.MEMO -> {
                                    if(scheduleData.tag_color != null){
                                        recordTextView.backgroundTintList = ColorStateList.valueOf(
                                            ContextCompat.getColor(context, CalendarUtils.getEventTagColor(
                                                scheduleData.tag_color!!)))
                                    }

                                }
                                CalendarGroup.DAYOFF->{
                                    recordTextView.backgroundTintList = ColorStateList.valueOf(
                                        ContextCompat.getColor(context, R.color.calendar_event_color_09))
                                }
                                CalendarGroup.ANNIVERSARY->{
                                    recordTextView.backgroundTintList = ColorStateList.valueOf(
                                        ContextCompat.getColor(context, R.color.calendar_event_color_10))
                                }
                            }
                            val sdf2 = SimpleDateFormat("MM")

                            if(selectedMonth != sdf2.format(date)){
                                recordTextView.alpha = 0.3f
                            }
                            val layoutEventTags = layoutEvent.tag as Array<String>
                            LOG_D(TAG, "layoutEventTags : ${layoutEventTags[0]}, ${layoutEventTags[1]},${layoutEventTags[2]}")
                            layoutDayList!![layoutEventTags[0].toInt()][layoutEventTags[1].toInt()].tag = arrayOf(layoutEventTags[0],layoutEventTags[1],layoutEventTags[2],scheduleData.idx.toString())

                            layoutEvent.addView(itemView)
                        }

                    }
                }

            }
        })
        viewModel.multiCheckLiveData.observe(this, Observer {
            multiCheckMode = it
            if(it){
                val selectedMonth = DateTime(millis).toString("MM")
                for(layoutMultiCheck in layoutMultiCheckSetFinList){
                    if(layoutMultiCheck.tag != null){
                        val tags = layoutMultiCheck.tag as Array<String>
                        if(tags[2] != null){
                            val sdf = SimpleDateFormat("yyyy-MM-dd")
                            val date = sdf.parse(tags[2])
                            val sdf2 = SimpleDateFormat("MM")
                            val scheduleMonth = sdf2.format(date)
                            LOG_D(TAG, "tags[2]: ${tags[2]}, scheduleMonth : ${scheduleMonth}")
                            if(scheduleMonth == selectedMonth){
                                layoutMultiCheck.isVisible = true
                                val checkImageView = layoutMultiCheck.getChildAt(0) as ImageView
                                checkImageView.setImageResource(R.drawable.bg_radio_unchecked_01)
                            }
                        }

                    }

                }
            }else{
                for(layoutMultiCheck in layoutMultiCheckSetFinList){
                    layoutMultiCheck.isVisible = false
                    val checkImageView = layoutMultiCheck.getChildAt(0) as ImageView
                    checkImageView.setImageResource(R.drawable.bg_radio_unchecked_01)


                }
            }



        })
        viewModel.multiCheckAddFinLiveData.observe(this, Observer {
            if(it){
                viewModel.getSchedule("getMonthSchedule", DateTime(millis).toString("yyyy"), DateTime(millis).toString("MM"),"")
            }
        })
    }

    fun setLayoutDayListBindings(){
        layoutDayList = arrayOf(
            arrayOf(binding.layoutDay11,binding.layoutDay21,binding.layoutDay31,binding.layoutDay41,binding.layoutDay51,binding.layoutDay61,binding.layoutDay71),
            arrayOf(binding.layoutDay12,binding.layoutDay22,binding.layoutDay32,binding.layoutDay42,binding.layoutDay52,binding.layoutDay62,binding.layoutDay72),
            arrayOf(binding.layoutDay13,binding.layoutDay23,binding.layoutDay33,binding.layoutDay43,binding.layoutDay53,binding.layoutDay63,binding.layoutDay73),
            arrayOf(binding.layoutDay14,binding.layoutDay24,binding.layoutDay34,binding.layoutDay44,binding.layoutDay54,binding.layoutDay64,binding.layoutDay74),
            arrayOf(binding.layoutDay15,binding.layoutDay25,binding.layoutDay35,binding.layoutDay45,binding.layoutDay55,binding.layoutDay65,binding.layoutDay75),
            arrayOf(binding.layoutDay16,binding.layoutDay26,binding.layoutDay36,binding.layoutDay46,binding.layoutDay56,binding.layoutDay66,binding.layoutDay76),
        )
        tvDayList = arrayOf(
            arrayOf(binding.tvDay11,binding.tvDay21,binding.tvDay31,binding.tvDay41,binding.tvDay51,binding.tvDay61,binding.tvDay71),
            arrayOf(binding.tvDay12,binding.tvDay22,binding.tvDay32,binding.tvDay42,binding.tvDay52,binding.tvDay62,binding.tvDay72),
            arrayOf(binding.tvDay13,binding.tvDay23,binding.tvDay33,binding.tvDay43,binding.tvDay53,binding.tvDay63,binding.tvDay73),
            arrayOf(binding.tvDay14,binding.tvDay24,binding.tvDay34,binding.tvDay44,binding.tvDay54,binding.tvDay64,binding.tvDay74),
            arrayOf(binding.tvDay15,binding.tvDay25,binding.tvDay35,binding.tvDay45,binding.tvDay55,binding.tvDay65,binding.tvDay75),
            arrayOf(binding.tvDay16,binding.tvDay26,binding.tvDay36,binding.tvDay46,binding.tvDay56,binding.tvDay66,binding.tvDay76),
        )
        layoutEventList = arrayOf(
            arrayOf(binding.layoutEvent11,binding.layoutEvent21,binding.layoutEvent31,binding.layoutEvent41,binding.layoutEvent51,binding.layoutEvent61,binding.layoutEvent71),
            arrayOf(binding.layoutEvent12,binding.layoutEvent22,binding.layoutEvent32,binding.layoutEvent42,binding.layoutEvent52,binding.layoutEvent62,binding.layoutEvent72),
            arrayOf(binding.layoutEvent13,binding.layoutEvent23,binding.layoutEvent33,binding.layoutEvent43,binding.layoutEvent53,binding.layoutEvent63,binding.layoutEvent73),
            arrayOf(binding.layoutEvent14,binding.layoutEvent24,binding.layoutEvent34,binding.layoutEvent44,binding.layoutEvent54,binding.layoutEvent64,binding.layoutEvent74),
            arrayOf(binding.layoutEvent15,binding.layoutEvent25,binding.layoutEvent35,binding.layoutEvent45,binding.layoutEvent55,binding.layoutEvent65,binding.layoutEvent75),
            arrayOf(binding.layoutEvent16,binding.layoutEvent26,binding.layoutEvent36,binding.layoutEvent46,binding.layoutEvent56,binding.layoutEvent66,binding.layoutEvent76),
        )
        layoutMultiCheckList = arrayOf(
            arrayOf(binding.layoutMultiCheck11,binding.layoutMultiCheck21,binding.layoutMultiCheck31,binding.layoutMultiCheck41,binding.layoutMultiCheck51,binding.layoutMultiCheck61,binding.layoutMultiCheck71),
            arrayOf(binding.layoutMultiCheck12,binding.layoutMultiCheck22,binding.layoutMultiCheck32,binding.layoutMultiCheck42,binding.layoutMultiCheck52,binding.layoutMultiCheck62,binding.layoutMultiCheck72),
            arrayOf(binding.layoutMultiCheck13,binding.layoutMultiCheck23,binding.layoutMultiCheck33,binding.layoutMultiCheck43,binding.layoutMultiCheck53,binding.layoutMultiCheck63,binding.layoutMultiCheck73),
            arrayOf(binding.layoutMultiCheck14,binding.layoutMultiCheck24,binding.layoutMultiCheck34,binding.layoutMultiCheck44,binding.layoutMultiCheck54,binding.layoutMultiCheck64,binding.layoutMultiCheck74),
            arrayOf(binding.layoutMultiCheck15,binding.layoutMultiCheck25,binding.layoutMultiCheck35,binding.layoutMultiCheck45,binding.layoutMultiCheck55,binding.layoutMultiCheck65,binding.layoutMultiCheck75),
            arrayOf(binding.layoutMultiCheck16,binding.layoutMultiCheck26,binding.layoutMultiCheck36,binding.layoutMultiCheck46,binding.layoutMultiCheck56,binding.layoutMultiCheck66,binding.layoutMultiCheck76),
        )
        val monthDateList = getMonthList(DateTime(millis))
        val selectedMonth = DateTime(millis).toString("MM")
        var j = 0
        var temp = 0
        for(i in monthDateList.indices){
            val dateTime = monthDateList[i]
            if(i > 0 && i % 7 == 0){
                j++
                temp = i
            }
            layoutDayList!![j][i-temp].tag = arrayOf(j.toString(),(i-temp).toString(),dateTime.toString("yyyy-MM-dd"),"-1")
            layoutDayListSetFinList.add(layoutDayList!![j][i-temp])
            tvDayList!![j][i-temp].tag = arrayOf(j.toString(),(i-temp).toString(),dateTime.toString("yyyy-MM-dd"))
            layoutEventList!![j][i-temp].tag = arrayOf(j.toString(),(i-temp).toString(),dateTime.toString("yyyy-MM-dd"))
            layoutMultiCheckList!![j][i-temp].tag = arrayOf(j.toString(),(i-temp).toString(),dateTime.toString("yyyy-MM-dd"),"unchecked")
            layoutMultiCheckList!![j][i-temp].setOnClickListener {
                if(it.tag != null){
                    val tags = it.tag as Array<String>
                    val checkedState = tags[3]
                    val checkImageView = (it as LinearLayout).getChildAt(0) as ImageView
                    if(checkedState == "checked"){
                        tags[3] = "unchecked"
                        checkImageView.setImageResource(R.drawable.bg_radio_unchecked_01)
                        if(dataRepo.calendar_add_schedule_multi_check_list != null && dataRepo.calendar_add_schedule_multi_check_list!!.size > 0){
                            dataRepo.calendar_add_schedule_multi_check_list?.removeAll { x->x == tags[2] }
                        }
                    }else{
                        tags[3] = "checked"
                        checkImageView.setImageResource(R.drawable.bg_radio_checked_01)
                        if(dataRepo.calendar_add_schedule_multi_check_list != null && dataRepo.calendar_add_schedule_multi_check_list!!.size > 0){
                            dataRepo.calendar_add_schedule_multi_check_list?.removeAll { x->x == tags[2] }
                        }
                        dataRepo.calendar_add_schedule_multi_check_list?.add(tags[2])
                    }
                }
            }

            tvDayList!![j][i-temp].text = dateTime.dayOfMonth.toString()
            layoutEventSetFinList.add(layoutEventList!![j][i-temp])
            layoutMultiCheckSetFinList.add(layoutMultiCheckList!![j][i-temp])


            if(selectedMonth != dateTime.toString("MM")){
                tvDayList!![j][i-temp].alpha = 0.3f
            }


        }


        for(layoutDay in layoutDayListSetFinList){

            layoutDay.setOnClickListener {
                for(tempLayoutDay in layoutDayListSetFinList){
                    tempLayoutDay.setBackgroundResource(R.color.transparent)

                }

                if (!multiCheckMode) {

                    if (it.tag != null) {
                        val tags = it.tag as Array<String>
                        LOG_D(TAG, "tag : ${tags[0]}, ${tags[1]}, ${tags[2]}, ${tags[3]} ")
                        if(selectedDate != tags[2] ){
                            selectedDate = tags[2]
                            layoutDay.setBackgroundResource(R.drawable.bg_calendar_day_selected_01)
                        }else{
                            if (tags[2] != null) {
                                var scheduleInfoData = IFSchedule.ScheduleInfo()
                                scheduleInfoData.startDate = tags[2]
                                dataRepo.selected_schedule_info_data = scheduleInfoData
                                if (tags[3] != null && tags[3] != "") {
                                    val selected_schedule_info_idx = tags[3].toInt()
                                    if (selected_schedule_info_idx != null && selected_schedule_info_idx != -1) {
                                        dataRepo.selected_schedule_info_idx = selected_schedule_info_idx
                                        if (viewModel.scheduleLiveData.value != null) {
                                            val scheduleInfo =
                                                viewModel.scheduleLiveData.value?.schedule_info
                                            if (scheduleInfo != null && scheduleInfo.size > 0) {
                                                LOG_D(TAG, "selected_schedule_info_idx : $selected_schedule_info_idx")
                                                if(scheduleInfo.find { x -> x.idx == selected_schedule_info_idx } != null){
                                                    scheduleInfoData =
                                                        scheduleInfo.find { x -> x.idx == selected_schedule_info_idx }!!
                                                    if (scheduleInfoData != null) {
                                                        dataRepo.selected_schedule_info_data =
                                                            scheduleInfoData
                                                        val intent = Intent(
                                                            context,
                                                            ScheduleInfoActivity::class.java
                                                        )
                                                        startActivity(intent)
                                                    } else {
                                                        val intent =
                                                            Intent(context, ScheduleAddActivity::class.java)
                                                        startActivity(intent)
                                                    }
                                                }else{
                                                    val intent =
                                                        Intent(context, ScheduleAddActivity::class.java)
                                                    startActivity(intent)
                                                }


                                            } else {
                                                val intent =
                                                    Intent(context, ScheduleAddActivity::class.java)
                                                startActivity(intent)
                                            }

                                        } else {
                                            val intent =
                                                Intent(context, ScheduleAddActivity::class.java)
                                            startActivity(intent)
                                        }


                                    } else {
                                        val intent = Intent(context, ScheduleAddActivity::class.java)
                                        startActivity(intent)
                                    }
                                } else {
                                    val intent = Intent(context, ScheduleAddActivity::class.java)
                                    startActivity(intent)
                                }
                            }
                        }




                    }

                }
            }
        }


    }

    fun setPageClear(){
        for(layoutEvent in layoutEventSetFinList){
            layoutEvent.removeAllViews()
        }
        for(tempLayoutDay in layoutDayListSetFinList){
            tempLayoutDay.setBackgroundResource(R.color.transparent)
            val tags = tempLayoutDay.tag as Array<String>
            tags[4] = "unselected"
        }
    }


    companion object {

        private const val MILLIS = "MILLIS"

        fun newInstance(millis: Long) = ScheduleCalendarFragment().apply {
            arguments = Bundle().apply {
                putLong(MILLIS, millis)
            }
        }
    }







}