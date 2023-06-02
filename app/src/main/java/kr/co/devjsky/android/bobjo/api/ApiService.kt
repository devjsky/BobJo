package kr.co.devjsky.android.bobjo.api


import kr.co.devjsky.android.bobjo.data.model.remote.IFMainStory
import kr.co.devjsky.android.bobjo.data.model.remote.IFSchedule
import kr.co.soundleader.android.lesson1vs5.data.model.api.IFDefault
import kr.co.soundleader.android.lesson1vs5.data.model.api.IFTEST
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    // test member token : pZFiUDyieYsUHmUixB_hjwBs96tzK0v6!7pQKX5ZRyPvU
    @FormUrlEncoded
    @POST("index.php")
    fun getIFTEST(@Field("user_token") user_token: String,
    ): Call<IFTEST>

    @FormUrlEncoded
    @POST("schedule.php")
    fun getMonthSchedule(@Field("user_token") user_token: String,
                    @Field("action") action: String,
                    @Field("year") year: String,
                    @Field("month") month: String
    ): Call<IFSchedule>

    @FormUrlEncoded
    @POST("schedule.php")
    fun getDaySchedule(@Field("user_token") user_token: String,
                         @Field("action") action: String,
                         @Field("start_date") start_date: String
    ): Call<IFSchedule>


    @FormUrlEncoded
    @POST("schedule.php")
    fun addScheduleMulti(@Field("user_token") user_token: String,
                         @Field("action") action: String,
                         @Field("dates") dates: String,
                         @Field("category_group") category_group: String,
                         @Field("title") title: String,
                         @Field("content") content: String,
                         @Field("state") state: String,
                         @Field("check_state") check_state: String,
                         @Field("top") top: String,
                         @Field("bigday") bigday: String,
                         @Field("allday") allday: String,
                         @Field("tag_color") tag_color: Int
    ): Call<IFDefault>

    @FormUrlEncoded
    @POST("schedule.php")
    fun removeSchedule(@Field("user_token") user_token: String,
                         @Field("action") action: String,
                         @Field("schedule_idx") schedule_idx: Int

    ): Call<IFDefault>
    @FormUrlEncoded
    @POST("schedule.php")
    fun addSchedule(@Field("user_token") user_token: String,
                         @Field("action") action: String,
                         @Field("start_date") start_date: String,
                         @Field("end_date") end_date: String,
                         @Field("category_group") category_group: String,
                         @Field("title") title: String,
                         @Field("content") content: String,
                         @Field("state") state: String,
                         @Field("check_state") check_state: String,
                         @Field("top") top: String,
                         @Field("bigday") bigday: String,
                         @Field("allday") allday: String,
                         @Field("tag_color") tag_color: Int
    ): Call<IFDefault>
    @FormUrlEncoded
    @POST("schedule.php")
    fun modifySchedule(@Field("user_token") user_token: String,
                    @Field("action") action: String,
                    @Field("start_date") start_date: String,
                    @Field("end_date") end_date: String,
                    @Field("category_group") category_group: String,
                    @Field("title") title: String,
                    @Field("content") content: String,
                    @Field("state") state: String,
                    @Field("check_state") check_state: String,
                    @Field("top") top: String,
                    @Field("bigday") bigday: String,
                    @Field("allday") allday: String,
                    @Field("tag_color") tag_color: Int,
                       @Field("schedule_idx") schedule_idx: Int
    ): Call<IFDefault>

    @FormUrlEncoded
    @POST("story/getMainStoryList.php")
    fun getMainStory(@Field("user_token") user_token: String
    ): Call<IFMainStory>
}