package ru.sdimosik.polyhabr.data.network.model.user

import com.google.gson.annotations.SerializedName

data class TokenRefreshRequest(
    @SerializedName("refreshToken")
    val refreshToken: String
)
