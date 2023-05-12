package kr.co.devjsky.android.bobjo.data.model.local

data class UserInfo (
    var userToken: String? = null,
    var name: String? = null,
    var email: String? = null,
    var phoneNumber: String? = null,
    var userType: String? = null,
    var gender: String? = null,
    var phoneNumberPublic: String? = null,
    var profileImgUrl: String? = null,
    var createdAt: String? = null,
    var access     : String? = null
)