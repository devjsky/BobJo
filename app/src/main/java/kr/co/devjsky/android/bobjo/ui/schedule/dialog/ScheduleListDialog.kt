package kr.co.devjsky.android.bobjo.ui.schedule.dialog

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import kr.co.devjsky.android.bobjo.api.ApiCallback
import kr.co.devjsky.android.bobjo.data.Constants.Companion.LOG_D
import kr.co.devjsky.android.bobjo.data.model.remote.IFSchedule
import kr.co.devjsky.android.bobjo.databinding.DialogScheduleListBinding
import kr.co.devjsky.android.bobjo.databinding.DialogScheduleSubMenuBinding
import kr.co.devjsky.android.bobjo.di.repository.ApiRepo
import kr.co.devjsky.android.bobjo.di.repository.DataRepo
import kr.co.devjsky.android.bobjo.ui.base.DialogViewModel
import kr.co.devjsky.android.bobjo.ui.schedule.activity.ScheduleAddActivity
import kr.co.devjsky.android.bobjo.ui.schedule.activity.ScheduleInfoActivity
import kr.co.devjsky.android.bobjo.ui.schedule.adapter.ScheduleListAdapter
import kr.co.devjsky.android.bobjo.ui.schedule.viewmodel.ScheduleViewModel
import kr.co.devjsky.android.bobjo.utils.DateUtils

import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.java.KoinJavaComponent
import java.text.SimpleDateFormat

class ScheduleListDialog() : DialogFragment() {
    val apiRepo : ApiRepo by inject()
    val dataRepo : DataRepo by inject()
    var binding:DialogScheduleListBinding? = null
    companion object {
        fun newInstance() = ScheduleListDialog()
    }

    private val _viewModel: ScheduleViewModel by sharedViewModel()
    val size = Point()

    interface OnBtnClickEventListener {
        fun addScheduleClick()
    }
    lateinit var onBtnClickEventListener: OnBtnClickEventListener

    fun setOnBtnClickListener(listener: OnBtnClickEventListener){
        onBtnClickEventListener = listener
    }
   // var scheduleDataList = ArrayList<IFSchedule.ScheduleInfo>()
    var scheduleListAdapter:ScheduleListAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogScheduleListBinding.inflate(inflater, container, false)
            .apply {
                this.lifecycleOwner = this@ScheduleListDialog
                this.viewModel = _viewModel
            }
        // 뒤로가기 버튼, 빈 화면 터치를 통해 dialog가 사라지지 않도록
//        setCancelable(true)


        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay

        display.getSize(size)

        binding!!.ivClose.setOnClickListener {
            dismiss()
        }



        binding!!.btnAddSchedule.setOnClickListener {

            val intent = Intent(context, ScheduleAddActivity::class.java)
            startActivity(intent)
        }
        scheduleListAdapter = ScheduleListAdapter()
        scheduleListAdapter?.itemClickListener = object : ScheduleListAdapter.OnItemClickListener{
            override fun OnItemClick(idx: Int?) {
                val intent = Intent(context, ScheduleInfoActivity::class.java)
                startActivity(intent)
            }
        }
        binding!!.rvList.adapter = scheduleListAdapter
        binding!!.rvList.layoutManager = LinearLayoutManager(context)
        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)




    }

    override fun onResume() {
        super.onResume()
        LOG_D("ScheduleListDialog", "onResume()")
        val window: Window? = dialog?.window
        window?.setGravity(Gravity.CENTER)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //   window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        val wlp: WindowManager.LayoutParams? = window?.attributes
        val metrics = resources.displayMetrics
        wlp?.width = metrics.widthPixels * 80 / 100
        wlp?.height =  metrics.heightPixels * 80 / 100
        window?.attributes = wlp
        if(binding != null){
            LOG_D("ScheduleListDialog", "aaa")
            if(dataRepo.selected_schedule_info_data != null){
                LOG_D("ScheduleListDialog", "bbb")
                if(dataRepo.selected_schedule_info_data?.startDate != null){
                    LOG_D("ScheduleListDialog", "ccc->${dataRepo.selected_schedule_info_data?.startDate}")
                    val selectedDate = DateUtils.getDateString(dataRepo.selected_schedule_info_data?.startDate.toString(), "yyyy-MM-dd (E)")
                    binding!!.tvTitle.text = selectedDate

                    apiRepo.getDaySchedule("getDaySchedule",
                        DateUtils.getDateString(dataRepo.selected_schedule_info_data?.startDate.toString(),"yyyy-MM-dd"), object : ApiCallback{
                            override fun <T> result(isSuccess: Boolean, code: Int, msg: String, data: T?) {
                                if(isSuccess){
                                    LOG_D("ScheduleListDialog", "ddd")
                                    val resultData = data as IFSchedule
                                    if(resultData != null){
                                        LOG_D("ScheduleListDialog", "eee")
                                        val scheduleDataList = resultData.schedule_info
                                        if(scheduleDataList != null && scheduleDataList.size > 0){
                                            LOG_D("ScheduleListDialog", "scheduleDataList.size:${scheduleDataList.size}")
                                            LOG_D("ScheduleListDialog", "scheduleListAdapter.itemCount:${scheduleListAdapter?.itemCount}")
                                            scheduleListAdapter?.setData(scheduleDataList)
                                            scheduleListAdapter?.notifyDataSetChanged()
                                            LOG_D("ScheduleListDialog", "scheduleListAdapter.itemCount:${scheduleListAdapter?.itemCount}")
                                        }else{
                                            LOG_D("ScheduleListDialog", "fff")
                                            scheduleListAdapter?.clear()

                                        }

                                    }

                                }

                            }
                        })




                }
            }
        }

    }

    fun setData(_scheduleInfoList: ArrayList<IFSchedule.ScheduleInfo>){
     //   scheduleDataList = _scheduleInfoList
       // scheduleListAdapter = ScheduleListAdapter(scheduleDataList)
    }


}