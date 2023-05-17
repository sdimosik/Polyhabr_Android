package ru.sdimosik.polyhabr.data.network.model.user

import com.google.gson.annotations.SerializedName

data class UserUpdateRequest(
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("surname")
    var surname: String? = null,
    @SerializedName("password")
    var password: String? = null,
    @SerializedName("newPassword")
    var newPassword: String? = null
)
