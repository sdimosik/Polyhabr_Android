package ru.sdimosik.polyhabr.data.network.model.user

import com.google.gson.annotations.SerializedName
import ru.sdimosik.polyhabr.data.db.model.AuthTokenEntity

data class LoginResponse(
    @SerializedName("accessToken")
    var accessToken: String?,
    @SerializedName("username")
    var username: String?,
    @SerializedName("isFirst")
    val isFirst: Boolean,
    @SerializedName("refreshToken")
    val refreshToken: String?,
    @SerializedName("idUser")
    val idUser: Long
)

fun LoginResponse.toEntity(password: String) = AuthTokenEntity(
    accessToken = accessToken!!,
    username = username!!,
    isFirst = isFirst,
    refreshToken = refreshToken!!,
    idUser = idUser,
    password = password
)
