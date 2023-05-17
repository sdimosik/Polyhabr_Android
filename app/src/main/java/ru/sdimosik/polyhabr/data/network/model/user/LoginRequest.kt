package ru.sdimosik.polyhabr.data.network.model.user

import com.google.gson.annotations.SerializedName
import ru.sdimosik.polyhabr.utils.RegexUtils

data class LoginRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
)

fun LoginRequest.validate(): Boolean {
    val usernameValidate = username.isNotEmpty()
            && username.isNotBlank()
            && username.length >= 3
            && username.length <= 20
            && RegexUtils.loginRegex.matches(username)

    val passwordValidate = password.isNotEmpty()
            && password.isNotBlank()
            && password.length >= 8
            && password.length <= 35
            && RegexUtils.passwordRegex.matches(password)

    return usernameValidate && passwordValidate
}
