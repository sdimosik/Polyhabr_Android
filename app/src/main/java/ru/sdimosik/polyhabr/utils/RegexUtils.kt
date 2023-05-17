package ru.sdimosik.polyhabr.utils

object RegexUtils {
    val loginRegex = "^[A-Za-z0-9]+\$".toRegex()
    val passwordRegex = "^[A-Za-z0-9]+\$".toRegex()
    val nameRegex = "^[A-Za-z]+\$".toRegex()
    val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)\$".toRegex()

    fun validatePassword(password: String): Pair<Boolean, String> {
        val passwordValidate = password.isNotEmpty()
                && password.isNotBlank()
                && password.length >= 8
                && password.length <= 35
                && passwordRegex.matches(password)

        if (!passwordValidate) {
            return false to "Невалидный пароль"
        }

        return true to ""
    }

    fun validateName(firstName: String): Pair<Boolean, String> {
        val firstNameValidate = firstName.isNotBlank()
                && firstName.isNotEmpty()
                && firstName.length >= 2
                && firstName.length <= 15
                && nameRegex.matches(firstName)

        if (!firstNameValidate) {
            return false to "Невалидное имя"
        }

        return true to ""
    }

    fun validateEmail(email: String): Pair<Boolean, String> {
        val emailValidate = email.isNotEmpty()
                && email.isNotBlank()
                && email.length >= 3
                && email.length <= 50
                && emailRegex.matches(email)

        if (!emailValidate) {
            return false to "Невалидная почта"
        }

        return true to ""
    }
}