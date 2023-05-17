package ru.sdimosik.polyhabr.data.network.model.user

import com.google.gson.annotations.SerializedName

data class TokenRefreshResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)
