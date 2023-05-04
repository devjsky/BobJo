package kr.co.devjsky.android.bobjo.data.model.remote

import com.google.gson.annotations.SerializedName



data class IFSchedule (

    @SerializedName("header"       ) var header      : Header?      = Header(),
    @SerializedName("schedule_info"    ) var schedule_info    : ArrayList<ScheduleInfo>?    = arrayListOf(),


    ) {
        data class Header (

            @SerializedName("code"    ) var code    : Int?    = null,
            @SerializedName("message" ) var message : String? = null

        )

        data class ScheduleInfo (

            @SerializedName("idx"          ) var idx         : Int? = null,
            @SerializedName("user_idx"     ) var userIdx     : Int? = null,
            @SerializedName("category_group"        ) var category_group       : String? = null,
            @SerializedName("title"        ) var title       : String? = null,
            @SerializedName("content"      ) var content     : String? = null,
            @SerializedName("state"        ) var state       : String? = null,
            @SerializedName("check_state"  ) var checkState  : String? = null,
            @SerializedName("top"          ) var top         : String? = null,
            @SerializedName("bigday"       ) var bigday      : String? = null,
            @SerializedName("created_date" ) var createdDate : String? = null,
            @SerializedName("updated_date" ) var updatedDate : String? = null,
            @SerializedName("start_date"   ) var startDate   : String? = null,
            @SerializedName("end_date"     ) var endDate     : String? = null,
            @SerializedName("allday"     ) var allday     : String? = null
        )




    }




