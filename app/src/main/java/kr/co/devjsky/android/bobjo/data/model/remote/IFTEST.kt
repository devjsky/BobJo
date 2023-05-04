package kr.co.soundleader.android.lesson1vs5.data.model.api

import com.google.gson.annotations.SerializedName



data class IFTEST (
    @SerializedName("header"       ) var header      : Header?      = Header(),
    @SerializedName("test_info"    ) var test_info    : ArrayList<TestInfo>    = arrayListOf(),
)

{
    data class Header (
        @SerializedName("code"    ) var code    : Int?    = null,
        @SerializedName("message" ) var message : String? = null
    )

    data class TestInfo (
        @SerializedName("idx"                 ) var idx               : Int? = null,
        @SerializedName("data"                 ) var data               : String? = null
    )

}



