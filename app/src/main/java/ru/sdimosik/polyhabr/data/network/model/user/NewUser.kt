package ru.sdimosik.polyhabr.data.network.model.user

import com.google.gson.annotations.SerializedName
import ru.sdimosik.polyhabr.utils.RegexUtils

data class NewUser(
    @SerializedName("username")
    var username: String,
    @SerializedName("firstName")
    var firstName: String,
    @SerializedName("lastName")
    var lastName: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password: String
)

fun NewUser.validate(): Pair<Boolean, String> {
    val usernameValidate = username.isNotEmpty()
            && username.isNotBlank()
            && username.length >= 3
            && username.length <= 20
            && RegexUtils.loginRegex.matches(username)

    if (!usernameValidate) {
        return false to "Невалидный логин"
    }

    val passwordValidate = password.isNotEmpty()
            && password.isNotBlank()
            && password.length >= 8
            && password.length <= 35
            && RegexUtils.passwordRegex.matches(password)

    if (!passwordValidate) {
        return false to "Невалидный пароль"
    }

    val firstNameValidate = firstName.isNotBlank()
            && firstName.isNotEmpty()
            && firstName.length >= 2
            && firstName.length <= 15
            && RegexUtils.nameRegex.matches(firstName)

    if (!firstNameValidate) {
        return false to "Невалидное имя"
    }

    val lastNameValidate = lastName.isNotBlank()
            && lastName.isNotEmpty()
            && lastName.length >= 2
            && lastName.length <= 15
            && RegexUtils.nameRegex.matches(firstName)

    if (!lastNameValidate) {
        return false to "Невалидная фамилия"
    }

    val emailValidate = email.isNotEmpty()
            && email.isNotBlank()
            && email.length >= 3
            && email.length <= 50
            && RegexUtils.emailRegex.matches(email)

    if (!emailValidate) {
        return false to "Невалидный почта"
    }

    return true to ""
}
