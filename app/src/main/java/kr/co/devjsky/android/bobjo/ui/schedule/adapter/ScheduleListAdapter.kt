package kr.co.devjsky.android.bobjo.ui.schedule.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.devjsky.android.bobjo.R
import kr.co.devjsky.android.bobjo.data.model.remote.IFSchedule
import kr.co.devjsky.android.bobjo.data.schedule.CalendarGroup
import kr.co.devjsky.android.bobjo.databinding.ItemScheduleListBinding
import kr.co.devjsky.android.bobjo.di.repository.DataRepo
import kr.co.devjsky.android.bobjo.ui.schedule.activity.ScheduleInfoActivity
import kr.co.devjsky.android.bobjo.utils.calendar.CalendarUtils
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent.inject
import java.text.SimpleDateFormat
import java.util.Date

class ScheduleListAdapter(): RecyclerView.Adapter<ScheduleListAdapter.ViewHolder>(){
    val dataRepo : DataRepo by inject(DataRepo::class.java)
    var items: ArrayList<IFSchedule.ScheduleInfo>? = null

    interface OnItemClickListener{
        fun OnItemClick(idx:Int?)
    }

    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(val binding: ItemScheduleListBinding):RecyclerView.ViewHolder(binding.root){
//        init {
//            binding.root.setOnClickListener {
//                dataRepo.selected_schedule_info_data = items[adapterPosition]
//                itemClickListener?.OnItemClick(items[adapterPosition].idx)
//            }
//        }
    }

    fun setData(datas: ArrayList<IFSchedule.ScheduleInfo>){
        items = datas
        notifyDataSetChanged()
    }
    fun clear(){

            items?.clear()
            notifyDataSetChanged()

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemScheduleListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            if(items != null){
                val data = items!![position]
                root.setOnClickListener {
                    dataRepo.selected_schedule_info_data = data
                    itemClickListener?.OnItemClick(data.idx)
                }


                val startDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data.startDate)
                val endDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data.endDate)
                if(SimpleDateFormat("yyyy-MM-dd").format(startDate) < SimpleDateFormat("yyyy-MM-dd").format(endDate)){
                    val formatStartDate = SimpleDateFormat("HH:mm").format(startDate)
                    val formatEndDate = SimpleDateFormat("yy.MM.dd (E) HH:mm").format(endDate)
                    tvTitle.text = data.title
                    tvStartDate.text = "$formatStartDate ~ $formatEndDate"
                }else{
                    val formatStartDate = SimpleDateFormat("HH:mm").format(startDate)
                    val formatEndDate = SimpleDateFormat("HH:mm").format(endDate)
                    tvTitle.text = data.title
                    tvStartDate.text = "$formatStartDate ~ $formatEndDate"
                }


                if(data.allday != null){
                    when(data.allday){
                        "Y" -> {
                            tvStartDate.text = "하루 종일"
                        }
                    }
                }

                if(data.tag_color != null){
                    ivTagColor.setBackgroundResource(CalendarUtils.getEventTagColor(data.tag_color!!))
                }
                if(data.category_group != null) {
                    when (data.category_group) {
                        CalendarGroup.MEMO -> {
                            ivCategoryGroup.setImageResource(R.drawable.ico_memo_01)

                        }

                        CalendarGroup.DAYOFF -> {
                            ivCategoryGroup.setImageResource(R.drawable.ico_dayoff_01)
                        }

                        CalendarGroup.ANNIVERSARY -> {
                            ivCategoryGroup.setImageResource(R.drawable.ico_anniversary_01)
                        }
                    }
                }
            }





        }
    }

    override fun getItemCount(): Int {
        return if(items != null){
            items!!.size
        }else{
            0
        }

    }
}