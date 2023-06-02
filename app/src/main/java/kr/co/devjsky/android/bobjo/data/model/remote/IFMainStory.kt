package kr.co.devjsky.android.bobjo.data.model.remote

import com.google.gson.annotations.SerializedName



data class IFMainStory (

    @SerializedName("header"       ) var header      : Header?      = Header(),
    @SerializedName("story_item_info"    ) var story_item_info    : ArrayList<StoryItemInfo>?    = arrayListOf(),


    ) {
        data class Header (

            @SerializedName("code"    ) var code    : Int?    = null,
            @SerializedName("message" ) var message : String? = null

        )

        data class StoryItemInfo (

            @SerializedName("idx"          ) var idx         : Int? = null,
            @SerializedName("user_idx"     ) var user_idx     : Int? = null,
            @SerializedName("content_description"        ) var content_description       : String? = null,
            @SerializedName("created_at"        ) var created_at       : String? = null,
            @SerializedName("updated_at"      ) var updated_at     : String? = null,
            @SerializedName("tags"        ) var tags       : String? = null,
            @SerializedName("state"  ) var state  : String? = null,
            @SerializedName("user_name"          ) var user_name         : String? = null,
            @SerializedName("content_img_file_list") var content_img_file_list : ArrayList<FileDataInfo>?    = arrayListOf(),
            @SerializedName("profile_img_url"          ) var profile_img_url         : String? = null

        )


}




