package kr.co.soundleader.android.lesson1vs5.data.model.api

import com.google.gson.annotations.SerializedName



data class IFDefault (
    @SerializedName("header"       ) var header      : Header?      = Header(),
)

{
    data class Header (
        @SerializedName("code"    ) var code    : Int?    = null,
        @SerializedName("message" ) var message : String? = null
    )



}



