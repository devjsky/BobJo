package kr.co.devjsky.android.bobjo.data.model.remote

import com.google.gson.annotations.SerializedName

data class FileDataInfo (

    @SerializedName("idx"          ) var idx         : Int? = null,
    @SerializedName("user_idx"     ) var user_idx     : Int? = null,
    @SerializedName("file_name"        ) var file_name       : String? = null,
    @SerializedName("file_category"        ) var file_category       : String? = null,
    @SerializedName("file_url"      ) var file_url     : String? = null,
    @SerializedName("created_at"        ) var created_at       : String? = null
)