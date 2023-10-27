package com.dicoding.subintermidate.data.local.user

import com.google.gson.annotations.SerializedName
data class User(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("userId")
    val userId: String? = "",

    @field:SerializedName("token")
    val token: String,

    @field:SerializedName("isLogin")
    val isLogin: Boolean
)