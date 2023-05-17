package ru.sdimosik.polyhabr.data.network.model.user

import com.google.gson.annotations.SerializedName

data class UserUpdateResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String
)
