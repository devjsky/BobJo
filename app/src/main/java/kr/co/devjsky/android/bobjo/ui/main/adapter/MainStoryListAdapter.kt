package kr.co.devjsky.android.bobjo.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.co.devjsky.android.bobjo.R
import kr.co.devjsky.android.bobjo.data.Constants.Companion.LOG_D
import kr.co.devjsky.android.bobjo.data.model.remote.IFMainStory
import kr.co.devjsky.android.bobjo.data.model.remote.IFSchedule
import kr.co.devjsky.android.bobjo.data.schedule.CalendarGroup
import kr.co.devjsky.android.bobjo.databinding.ItemScheduleListBinding
import kr.co.devjsky.android.bobjo.databinding.ItemStoryMainListBinding
import kr.co.devjsky.android.bobjo.di.repository.DataRepo
import kr.co.devjsky.android.bobjo.ui.schedule.activity.ScheduleInfoActivity
import kr.co.devjsky.android.bobjo.utils.DateUtils
import kr.co.devjsky.android.bobjo.utils.calendar.CalendarUtils
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent.inject
import java.text.SimpleDateFormat
import java.util.Date

class MainStoryListAdapter(): RecyclerView.Adapter<MainStoryListAdapter.ViewHolder>(){
    val dataRepo : DataRepo by inject(DataRepo::class.java)
    var storyItemDatas: ArrayList<IFMainStory.StoryItemInfo>? = null

    interface OnItemClickListener{
        fun OnItemClick(idx:Int?)
    }

    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(val binding: ItemStoryMainListBinding):RecyclerView.ViewHolder(binding.root){
//        init {
//            binding.root.setOnClickListener {
//                dataRepo.selected_schedule_info_data = items[adapterPosition]
//                itemClickListener?.OnItemClick(items[adapterPosition].idx)
//            }
//        }
    }

    fun setData(datas: ArrayList<IFMainStory.StoryItemInfo>){
        if(storyItemDatas == null){
            storyItemDatas = ArrayList()
        }
        storyItemDatas = datas
        notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun addData(datas: ArrayList<IFMainStory.StoryItemInfo>){
        LOG_D("MainStoryListAdapter", "addData()")
        if(storyItemDatas == null){
            LOG_D("MainStoryListAdapter", "ArrayList()")
            storyItemDatas = ArrayList()
        }
        LOG_D("MainStoryListAdapter", "addAll() datas.size${datas.size}")
        for(data in datas){
            storyItemDatas?.add(data)
        }
        notifyDataSetChanged()
    }
    fun clearData(){
            LOG_D("MainStoryListAdapter", "items.SIZE : ${storyItemDatas?.size}")
        storyItemDatas?.clear()
            notifyDataSetChanged()


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemStoryMainListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            if(storyItemDatas != null){
                layoutSkeletonBody.showSkeleton()
                val data = storyItemDatas!![position]
                root.setOnClickListener {
                }
                if(data.profile_img_url != null){
                    Glide.with(holder.itemView)
                        .load(data.profile_img_url)
                        .error(R.color.skeleton_color_01)
                        .override(256,256)
                        .into(ivProfile)
                }else{
                    ivProfile.setImageResource(R.color.skeleton_color_01)
                }
                if(data.user_name != null){
                    tvName01.text = data.user_name
                    tvName02.text = data.user_name
                }else{
                    tvName01.text = "정보 없음"
                    tvName02.text = "정보 없음"
                }
                if(data.content_description != null){
                    tvContentDescription.text = data.content_description
                }else{
                    tvContentDescription.text = ""
                }

                if(data.created_at != null){
                    tvCreatedAt.text = DateUtils.getDateString(data.created_at.toString(), "yyyy-MM-dd")
                }else{
                    tvCreatedAt.text = "날짜 없음"
                }
                if(data.content_img_file_list != null && data.content_img_file_list!!.size > 0){
                    Glide.with(holder.itemView)
                        .load(data.content_img_file_list!![0].file_url)
                        .error(R.color.skeleton_color_01)
                        .override(1080,1080)
                        .into(ivContentImage)
                }else{
                    ivProfile.setImageResource(R.color.skeleton_color_01)
                }
                if(data.tags != null){
                    tvTags.text = data.tags
                }else{
                    tvTags.text = ""
                }

                layoutSkeletonBody.showOriginal()
            }





        }
    }

    override fun getItemCount(): Int {
        return if(storyItemDatas != null){
            storyItemDatas!!.size
        }else{
            0
        }

    }
}